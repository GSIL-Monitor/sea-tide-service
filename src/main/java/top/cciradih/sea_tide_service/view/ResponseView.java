package top.cciradih.sea_tide_service.view;

import lombok.Data;

@Data
public class ResponseView {
    private Integer code;
    private String message;
    private Object object;

    public ResponseView(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseView(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }
}
