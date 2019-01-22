package top.cciradih.sea_tide_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class CorporationTax {
    @Id
    private Long id;
    private Long amount;
    private Date date;
    private Long characterId;
    private Long corporationId;

    public CorporationTax() {
    }

    public CorporationTax(Long id, Long amount, Date date, Long characterId, Long corporationId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.characterId = characterId;
        this.corporationId = corporationId;
    }
}
