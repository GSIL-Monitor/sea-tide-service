package top.cciradih.sea_tide_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Administrator {
    @Id
    private Long id;
    private String name;

    public Administrator() {
    }

    public Administrator(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
