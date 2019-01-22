package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CharacterComponent {
    public JsonNode getToken(String code) throws SeaTideException {
        String url = "https://login.eveonline.com/v2/oauth/token/";

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("code", code);

        HttpHeaders httpHeaders = new HttpHeaders();
//        String authorization = "Basic " + Base64.getEncoder().encodeToString("d842cdda9617448b980e7eeceeff0288:NWTgHV5iy2EDz2uzoRJ8UbDEUoeO0mhujUWPLBMd".getBytes());
        String authorization = "Basic " + Base64.getEncoder().encodeToString("32c2376ce40d4ec9b1c96d99da5dbb59:wlSTgXEgPTv4d1X97nVqIkxB63tEWcRFHQYb0GL5".getBytes());
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authorization);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, httpHeaders);

        try {
            String json = new RestTemplate().postForObject(url, httpEntity, String.class);
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public JsonNode refreshToken(String refreshToken) throws SeaTideException {
        String url = "https://login.eveonline.com/v2/oauth/token/";

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "refresh_token");
        body.put("refresh_token", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        String authorization = "Basic " + Base64.getEncoder().encodeToString("32c2376ce40d4ec9b1c96d99da5dbb59:wlSTgXEgPTv4d1X97nVqIkxB63tEWcRFHQYb0GL5".getBytes());
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authorization);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, httpHeaders);

        try {
            String json = new RestTemplate().postForObject(url, httpEntity, String.class);
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public JsonNode getRole(Long id, String accessToken) throws SeaTideException {
        String url = "https://esi.evetech.net/latest/characters/" + id + "/roles/?datasource=tranquility&token=" + accessToken;
        try {
            String json = new RestTemplate().getForObject(url, String.class);
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }
}
