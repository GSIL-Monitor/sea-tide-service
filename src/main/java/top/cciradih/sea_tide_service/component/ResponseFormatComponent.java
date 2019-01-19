package top.cciradih.sea_tide_service.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.view.ResponseView;

@Component
public class ResponseFormatComponent {
    public ResponseEntity<ResponseView> format(StatusEnumeration statusEnumeration, HttpStatus httpStatus) {
        Integer code = statusEnumeration.getCode();
        String message = statusEnumeration.getMessage();
        ResponseView responseView = new ResponseView(code, message);
        return new ResponseEntity<>(responseView, httpStatus);
    }

    public ResponseEntity<ResponseView> format(StatusEnumeration statusEnumeration, HttpStatus httpStatus, Object object) {
        Integer code = statusEnumeration.getCode();
        String message = statusEnumeration.getMessage();
        ResponseView responseView = new ResponseView(code, message, object);
        return new ResponseEntity<>(responseView, httpStatus);
    }
}
