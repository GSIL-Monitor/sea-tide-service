package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CorporationTaxView {
    private Long id;
    private Long amount;
    private Date date;
    private Long characterId;
    private Long corporationId;

    public CorporationTaxView() {
    }

    public CorporationTaxView(Long amount) {
        this.amount = amount;
    }

    public CorporationTaxView(Long id, Long amount, Date date, Long characterId, Long corporationId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.characterId = characterId;
        this.corporationId = corporationId;
    }
}
