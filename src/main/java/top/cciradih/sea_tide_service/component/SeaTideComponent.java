package top.cciradih.sea_tide_service.component;

import org.springframework.stereotype.Component;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class SeaTideComponent {
    public Date format(String dateString) throws SeaTideException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone timeZone = TimeZone.getTimeZone("Atlantic/Reykjavik");
        simpleDateFormat.setTimeZone(timeZone);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }
}
