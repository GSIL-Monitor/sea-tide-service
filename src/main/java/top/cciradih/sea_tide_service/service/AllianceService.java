package top.cciradih.sea_tide_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.entity.Alliance;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.repository.AllianceRepository;
import top.cciradih.sea_tide_service.view.AllianceView;
import top.cciradih.sea_tide_service.view.ResponseView;

@Service
public class AllianceService {
    @Autowired
    private AllianceRepository allianceRepository;
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
}
