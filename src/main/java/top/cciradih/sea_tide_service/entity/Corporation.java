package top.cciradih.sea_tide_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Corporation {
    @Id
    private Long id;
    private String name;
    private Long ceoId;
    private Long allianceId;
    private Long memberCount;
    private Double taxRate;
    private String ticker;

    public Corporation() {
    }

    public Corporation(Long id, String name, Long ceoId, Long allianceId, Long memberCount, Double taxRate, String ticker) {
        this.id = id;
        this.name = name;
        this.ceoId = ceoId;
        this.allianceId = allianceId;
        this.memberCount = memberCount;
        this.taxRate = taxRate;
        this.ticker = ticker;
    }
}
