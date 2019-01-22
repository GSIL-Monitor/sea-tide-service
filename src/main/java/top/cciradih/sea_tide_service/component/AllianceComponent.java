package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;

import java.io.IOException;

@Component
public class AllianceComponent {
    public JsonNode getCorporation() throws SeaTideException {
        Request request = new Request.Builder().get().url("https://esi.evetech.net/latest/alliances/99007362/corporations/?datasource=tranquility").build();
        try {
            String json = new OkHttpClient().newCall(request).execute().body().string();
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }
}
