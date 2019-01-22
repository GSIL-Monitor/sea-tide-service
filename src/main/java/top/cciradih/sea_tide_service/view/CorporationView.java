package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CorporationView {
    private Long id;
    private String name;
    private Long ceoId;
    private Long allianceId;
    private Long memberCount;
    private Double taxRate;
    private String ticker;

    public CorporationView() {
    }

    public CorporationView(Long id, String name, Long ceoId, Long allianceId, Long memberCount, Double taxRate, String ticker) {
        this.id = id;
        this.name = name;
        this.ceoId = ceoId;
        this.allianceId = allianceId;
        this.memberCount = memberCount;
        this.taxRate = taxRate;
        this.ticker = ticker;
    }
}
