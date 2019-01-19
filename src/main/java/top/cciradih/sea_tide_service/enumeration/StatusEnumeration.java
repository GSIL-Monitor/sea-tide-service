package top.cciradih.sea_tide_service.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnumeration {
    S0(0, "SUCCESS"),
    F1(-1, "...");

    private Integer code;
    private String message;

    StatusEnumeration(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
