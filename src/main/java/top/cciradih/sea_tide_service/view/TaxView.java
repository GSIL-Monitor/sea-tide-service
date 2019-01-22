package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class TaxView {
    private Long amount;
    private Long characterId;
    private Long corporationId;

    public TaxView() {
    }

    public TaxView(Long amount) {
        this.amount = amount;
    }

    public TaxView(Long amount, Long characterId) {
        this.amount = amount;
        this.characterId = characterId;
    }

    public TaxView(Long amount, Long characterId, Long corporationId) {
        this.amount = amount;
        this.characterId = characterId;
        this.corporationId = corporationId;
    }
}
