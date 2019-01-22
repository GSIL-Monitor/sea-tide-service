package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;

@Component
public class CorporationComponent {
    public List<JsonNode> getWalletJournal(Long id, Integer page, String accessToken, List<JsonNode> jsonNodeList) {
        Request request = new Request.Builder().get().url("https://esi.evetech.net/latest/corporations/" + id + "/wallets/1/journal/?datasource=tranquility&page=" + page + "&token=" + accessToken).build();
        try {
            String json = new OkHttpClient().newCall(request).execute().body().string();
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            jsonNodeList.add(jsonNode);
            if (jsonNode.size() == 1000) {
                getWalletJournal(id, page + 1, accessToken, jsonNodeList);
            }
            return jsonNodeList;
        } catch (HttpClientErrorException | IOException e) {
            return getWalletJournal(id, page, accessToken, jsonNodeList);
        }
    }
}
