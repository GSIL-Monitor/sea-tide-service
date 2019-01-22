package top.cciradih.sea_tide_service.exception;

import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;

public class SeaTideException extends Exception {
    private SeaTideException(String message) {
        super(message);
    }

    public static SeaTideException with(StatusEnumeration statusEnumeration) {
        String message = statusEnumeration.getMessage();
        return new SeaTideException(message);
    }
}
