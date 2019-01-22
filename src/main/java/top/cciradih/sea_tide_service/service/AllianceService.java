package top.cciradih.sea_tide_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.entity.Alliance;
import top.cciradih.sea_tide_service.entity.Tax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.AdministratorRepository;
import top.cciradih.sea_tide_service.repository.AllianceRepository;
import top.cciradih.sea_tide_service.repository.TaxRepository;
import top.cciradih.sea_tide_service.view.AllianceView;
import top.cciradih.sea_tide_service.view.ResponseView;
import top.cciradih.sea_tide_service.view.TaxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AllianceService {
    @Autowired
    private AllianceRepository allianceRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    public ResponseEntity<ResponseView> save(AllianceView allianceView) {
        Long id = allianceView.getId();
        Long executorCorporationId = allianceView.getExecutorCorporationId();
        String name = allianceView.getName();
        String ticker = allianceView.getTicker();
        Alliance alliance = new Alliance(id, executorCorporationId, name, ticker);
        alliance = allianceRepository.save(alliance);
        id = alliance.getId();
        executorCorporationId = alliance.getExecutorCorporationId();
        name = alliance.getName();
        ticker = alliance.getTicker();
        allianceView = new AllianceView(id, executorCorporationId, name, ticker);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, allianceView);
    }

    public ResponseEntity<ResponseView> getTax(Long characterId, Long startDate, Long endDate) {
        try {
            administratorRepository.findById(characterId).orElseThrow(() -> SeaTideException.with(StatusEnumeration.F5));
        } catch (SeaTideException e) {
            String message = e.getMessage();
            return responseFormatComponent.format(message, HttpStatus.BAD_REQUEST);
        }
        Date start = new Date(startDate);
        Date end = new Date(endDate);
        List<Tax> taxList = taxRepository.findByDateBetween(start, end);
        List<TaxView> taxViewList = new ArrayList<>();
        for (Tax tax : taxList) {
            Long amount = tax.getAmount();
            Long corporationId = tax.getCorporationId();
            TaxView taxView = new TaxView(amount, characterId, corporationId);
            taxViewList.add(taxView);
        }
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, taxViewList);
    }
}
