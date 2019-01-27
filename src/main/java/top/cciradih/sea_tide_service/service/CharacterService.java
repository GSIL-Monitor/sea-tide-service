package top.cciradih.sea_tide_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.CharacterComponent;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.entity.Tax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.AllianceRepository;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.repository.CorporationRepository;
import top.cciradih.sea_tide_service.repository.TaxRepository;
import top.cciradih.sea_tide_service.view.CharacterView;
import top.cciradih.sea_tide_service.view.ResponseView;
import top.cciradih.sea_tide_service.view.TaxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CharacterService {
    private static final Long ALLIANCE_ID = 99007362L;

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private AllianceRepository allianceRepository;
    @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private CharacterComponent characterComponent;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    public ResponseEntity<ResponseView> getId(String code) {
        JsonNode tokenJsonNode;
        try {
            tokenJsonNode = characterComponent.getToken(code);
        } catch (SeaTideException e) {
            String message = e.getMessage();
            return responseFormatComponent.format(message, HttpStatus.BAD_REQUEST);
        }
        String accessToken = tokenJsonNode.path("access_token").asText();
        String refreshToken = tokenJsonNode.path("refresh_token").asText();
        DecodedJWT jwt = JWT.decode(accessToken);
        String subject = jwt.getSubject();
        int lastIndex = subject.lastIndexOf(":") + 1;
        String idString = subject.substring(lastIndex);
        Long id = Long.valueOf(idString);
        Character character = characterRepository.findById(id).orElse(new Character(id, refreshToken));
        character = characterRepository.save(character);
        id = character.getId();
        CharacterView characterView = new CharacterView(id);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterView);
    }

    public ResponseEntity<ResponseView> save(CharacterView characterView) {
        Long allianceId = characterView.getAllianceId();
        boolean belong = ALLIANCE_ID.equals(allianceId);
        Long id = characterView.getId();
        Long corporationId = characterView.getCorporationId();
        if (belong) {
            String name = characterView.getName();
            SeaTideException seaTideException = SeaTideException.with(StatusEnumeration.F3);
            Character character;
            try {
                character = characterRepository.findById(id).orElseThrow(() -> seaTideException);
            } catch (SeaTideException e) {
                String message = e.getMessage();
                return responseFormatComponent.format(message, HttpStatus.NOT_FOUND);
            }
            character.setName(name);
            character.setAllianceId(allianceId);
            character.setCorporationId(corporationId);
            character = characterRepository.save(character);

            id = character.getId();
            name = character.getName();
            allianceId = character.getAllianceId();
            corporationId = character.getCorporationId();
            Boolean verification = character.getVerification();
            characterView = new CharacterView(id, name, allianceId, corporationId, verification);
            return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterView);
        } else {
            characterRepository.deleteById(id);
            try {
                corporationRepository.deleteById(corporationId);
            } catch (EmptyResultDataAccessException ignored) {
            }
            return responseFormatComponent.format(StatusEnumeration.F4, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseView> getTax(Long characterId, Long startDate, Long endDate) {
        Date start = new Date(startDate);
        Date end = new Date(endDate);
        List<Tax> taxList = taxRepository.findByCharacterIdAndDateBetween(characterId, start, end);
        List<TaxView> taxViewList = new ArrayList<>();
        for (Tax tax : taxList) {
            Long amount = tax.getAmount();
            TaxView taxView = new TaxView(amount);
            taxViewList.add(taxView);
        }
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, taxViewList);
    }

    public ResponseEntity<ResponseView> getVerificationCode(Long characterId, String email) {
        Character character;
        try {
            character = characterRepository.findById(characterId).orElseThrow(() -> SeaTideException.with(StatusEnumeration.F1));
        } catch (SeaTideException e) {
            return responseFormatComponent.format(StatusEnumeration.F3, HttpStatus.BAD_REQUEST);
        }
        Integer verificationCode = characterComponent.sendVerificationCode(email);
        character.setEmail(email);
        character.setVerificationCode(verificationCode);
        characterRepository.save(character);
        CharacterView characterView = new CharacterView(verificationCode);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterView);
    }

    public ResponseEntity<ResponseView> verify(Long characterId, Integer verificationCode, String nickname) {
        Character character;
        try {
            character = characterRepository.findById(characterId).orElseThrow(() -> SeaTideException.with(StatusEnumeration.F1));
        } catch (SeaTideException e) {
            return responseFormatComponent.format(StatusEnumeration.F3, HttpStatus.BAD_REQUEST);
        }
        Integer existVerificationCode = character.getVerificationCode();
        boolean verification = verificationCode.equals(existVerificationCode);
        if (verification) {
            character.setVerification(true);
            character.setNickname(nickname);
            characterRepository.save(character);
            String name = character.getName();
            CharacterView characterView = new CharacterView(name);
            return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterView);
        } else {
            return responseFormatComponent.format(StatusEnumeration.F4, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseView> getGroup(Long characterId) {
        Character existCharacter;
        try {
            existCharacter = characterRepository.findById(characterId).orElseThrow(() -> SeaTideException.with(StatusEnumeration.F1));
        } catch (SeaTideException e) {
            return responseFormatComponent.format(StatusEnumeration.F3, HttpStatus.BAD_REQUEST);
        }
        String nickname = existCharacter.getNickname();
        String email = existCharacter.getEmail();
        List<Character> characterList = characterRepository.findByEmailAndNicknameAndVerification(email, nickname, true);
        List<CharacterView> characterViewList = new ArrayList<>();
        for (Character character : characterList) {
            String name = character.getName();
            CharacterView characterView = new CharacterView(name);
            characterViewList.add(characterView);
        }
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterViewList);
    }
}
