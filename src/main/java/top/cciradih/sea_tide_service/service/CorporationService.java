package top.cciradih.sea_tide_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.entity.Corporation;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.repository.CorporationRepository;
import top.cciradih.sea_tide_service.view.CorporationView;
import top.cciradih.sea_tide_service.view.ResponseView;

@Service
public class CorporationService {
    @Autowired
    private CorporationRepository corporationRepository;
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
}
