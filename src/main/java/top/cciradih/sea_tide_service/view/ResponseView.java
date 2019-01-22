package top.cciradih.sea_tide_service.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ResponseView {
    private String message;
    private Object object;

    public ResponseView(String message) {
        this.message = message;
    }

    public ResponseView(String message, Object object) {
        this.message = message;
        this.object = object;
    }
}
