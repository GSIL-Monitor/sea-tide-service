package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CharacterView {
    private Long id;
    private String name;
    private Long allianceId;
    private Long corporationId;

    public CharacterView() {
    }

    public CharacterView(Long id) {
        this.id = id;
    }

    public CharacterView(Long id, String name, Long allianceId, Long corporationId) {
        this.id = id;
        this.name = name;
        this.allianceId = allianceId;
        this.corporationId = corporationId;
    }
}
