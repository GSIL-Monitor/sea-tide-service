package top.cciradih.sea_tide_service.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnumeration {
    S0("成功"),
    F1("系统错误"),
    F2("ESI 错误"),
    F3("未找到"),
    F4("不属于"),
    F5("无权限");

    private String message;

    StatusEnumeration(String message) {
        this.message = message;
    }
}
