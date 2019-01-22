package top.cciradih.sea_tide_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Alliance {
    @Id
    private Long id;
    private Long executorCorporationId;
    private String name;
    private String ticker;

    public Alliance() {
    }

    public Alliance(Long id, Long executorCorporationId, String name, String ticker) {
        this.id = id;
        this.executorCorporationId = executorCorporationId;
        this.name = name;
        this.ticker = ticker;
    }
}
