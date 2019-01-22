package top.cciradih.sea_tide_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Entity
@Data
public class Character {
    @Id
    private Long id;
    private String name;
    private Long allianceId;
    private Long corporationId;
    private String refreshToken;
    private Boolean administrator = false;

    public Character() {
    }

    public Character(Long id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }

    public Character(Long id, String name, Long allianceId, Long corporationId) {
        this.id = id;
        this.name = name;
        this.allianceId = allianceId;
        this.corporationId = corporationId;
    }
}
