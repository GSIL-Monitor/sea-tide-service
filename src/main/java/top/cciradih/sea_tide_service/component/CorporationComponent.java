package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;

import java.io.IOException;
import java.util.List;

@Component
public class CorporationComponent {
    public JsonNode get(Long id) throws SeaTideException {
        String url = "https://esi.evetech.net/latest/corporations/" + id + "/?datasource=tranquility";
        try {
            String json = new RestTemplate().getForObject(url, String.class);
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public List<JsonNode> getWalletJournal(Long id, Integer page, String accessToken, List<JsonNode> jsonNodeList) throws SeaTideException {
        String url = "https://esi.evetech.net/latest/corporations/" + id + "/wallets/1/journal/?datasource=tranquility&page=" + page + "&token=" + accessToken;
        try {
            String json = new RestTemplate().getForObject(url, String.class);
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            jsonNodeList.add(jsonNode);
            if (jsonNode.size() == 1000) {
                getWalletJournal(id, page + 1, accessToken, jsonNodeList);
            }
            return jsonNodeList;
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }
}