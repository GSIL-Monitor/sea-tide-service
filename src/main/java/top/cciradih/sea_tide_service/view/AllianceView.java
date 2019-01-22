package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AllianceView {
    private Long id;
    private Long executorCorporationId;
    private String name;
    private String ticker;

    public AllianceView() {
    }

    public AllianceView(Long id, Long executorCorporationId, String name, String ticker) {
        this.id = id;
        this.executorCorporationId = executorCorporationId;
        this.name = name;
        this.ticker = ticker;
    }
}
