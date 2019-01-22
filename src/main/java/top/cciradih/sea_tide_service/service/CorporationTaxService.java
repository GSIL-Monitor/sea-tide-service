package top.cciradih.sea_tide_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.CharacterComponent;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.entity.CorporationTax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.repository.CorporationTaxRepository;
import top.cciradih.sea_tide_service.view.CorporationTaxView;
import top.cciradih.sea_tide_service.view.ResponseView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CorporationTaxService {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CorporationTaxRepository corporationTaxRepository;
    @Autowired
    private CharacterComponent characterComponent;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    public ResponseEntity<ResponseView> get(Long characterId, Long startDate, Long endDate) {
        Date start = new Date(startDate);
        Date end = new Date(endDate);
        List<CorporationTax> corporationTaxList = corporationTaxRepository.findByCharacterIdAndDateBetween(characterId, start, end);
        List<CorporationTaxView> corporationTaxViewList = new ArrayList<>();
        corporationTaxList.parallelStream().forEach(corporationTax -> {
            Long amount = corporationTax.getAmount();
            CorporationTaxView corporationView = new CorporationTaxView(amount);
            corporationTaxViewList.add(corporationView);
        });
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, corporationTaxViewList);
    }

    public ResponseEntity<ResponseView> get(Long characterId, Long corporationId) {
        Character character;
        try {
            character = characterRepository.findById(characterId).orElseThrow(() -> SeaTideException.with(StatusEnumeration.F3));
        } catch (SeaTideException e) {
            String message = e.getMessage();
            return responseFormatComponent.format(message, HttpStatus.BAD_REQUEST);
        }
        String refreshToken = character.getRefreshToken();
        JsonNode tokenJsonNode;
        try {
            tokenJsonNode = characterComponent.refreshToken(refreshToken);
        } catch (SeaTideException e) {
            String message = e.getMessage();
            return responseFormatComponent.format(message, HttpStatus.BAD_REQUEST);
        }
        String accessToken = tokenJsonNode.path("access_token").asText();
        JsonNode roleJsonNode;
        try {
            roleJsonNode = characterComponent.getRole(characterId, accessToken);
        } catch (SeaTideException e) {
            String message = e.getMessage();
            return responseFormatComponent.format(message, HttpStatus.BAD_REQUEST);
        }
        String role = roleJsonNode.path("roles").get(0).asText();
        boolean director = "Director".equals(role);
        if (director) {

        }
        return null;
    }
}
