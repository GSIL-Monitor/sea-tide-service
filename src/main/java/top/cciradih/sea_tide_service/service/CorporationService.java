package top.cciradih.sea_tide_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.CharacterComponent;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.component.ScheduledComponent;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.entity.Corporation;
import top.cciradih.sea_tide_service.entity.Tax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.repository.CorporationRepository;
import top.cciradih.sea_tide_service.repository.TaxRepository;
import top.cciradih.sea_tide_service.view.CorporationView;
import top.cciradih.sea_tide_service.view.ResponseView;
import top.cciradih.sea_tide_service.view.TaxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CorporationService {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterComponent characterComponent;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private ScheduledComponent scheduledComponent;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    public ResponseEntity<ResponseView> save(CorporationView corporationView) {
        Long allianceId = corporationView.getAllianceId();
        Long id = corporationView.getId();
        String name = corporationView.getName();
        Long ceoId = corporationView.getCeoId();
        Long memberCount = corporationView.getMemberCount();
        Double taxRate = corporationView.getTaxRate();
        String ticker = corporationView.getTicker();
        Corporation corporation = new Corporation(id, name, ceoId, allianceId, memberCount, taxRate, ticker);
        corporation = corporationRepository.save(corporation);
        id = corporation.getId();
        name = corporation.getName();
        ceoId = corporation.getCeoId();
        allianceId = corporation.getAllianceId();
        memberCount = corporation.getMemberCount();
        taxRate = corporation.getTaxRate();
        ticker = corporation.getTicker();
        corporationView = new CorporationView(id, name, ceoId, allianceId, memberCount, taxRate, ticker);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, corporationView);
    }

    public void getTax() {
        try {
            scheduledComponent.getTax();
        } catch (SeaTideException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<ResponseView> getTax(Long characterId, Long corporationId, Long startDate, Long endDate) {
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
            Date start = new Date(startDate);
            Date end = new Date(endDate);
            List<Tax> taxList = taxRepository.findByCorporationIdAndDateBetween(corporationId, start, end);
            List<TaxView> taxViewList = new ArrayList<>();
            for (Tax tax : taxList) {
                Long taxCharacterId = tax.getCharacterId();
                Long amount = tax.getAmount();
                TaxView taxView = new TaxView(amount, taxCharacterId);
                taxViewList.add(taxView);
            }
            return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, taxViewList);
        } else {
            return responseFormatComponent.format(StatusEnumeration.F5, HttpStatus.BAD_REQUEST);
        }
    }
}
