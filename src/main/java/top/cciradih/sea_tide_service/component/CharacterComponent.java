package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;

import java.io.IOException;
import java.util.Random;

@Component
public class CharacterComponent {
    @Autowired
    private JavaMailSender javaMailSender;

    public JsonNode getToken(String code) throws SeaTideException {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .build();
//        String authorization = Credentials.basic("32c2376ce40d4ec9b1c96d99da5dbb59", "wlSTgXEgPTv4d1X97nVqIkxB63tEWcRFHQYb0GL5");
        String authorization = Credentials.basic("d842cdda9617448b980e7eeceeff0288", "NWTgHV5iy2EDz2uzoRJ8UbDEUoeO0mhujUWPLBMd");
        Request request = new Request.Builder()
                .post(formBody)
                .url("https://login.eveonline.com/v2/oauth/token/")
                .addHeader("Authorization", authorization)
                .build();
        try {
            String json = new OkHttpClient().newCall(request).execute().body().string();
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public JsonNode refreshToken(String refreshToken) throws SeaTideException {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", refreshToken)
                .build();
//        String authorization = Credentials.basic("32c2376ce40d4ec9b1c96d99da5dbb59", "wlSTgXEgPTv4d1X97nVqIkxB63tEWcRFHQYb0GL5");
        String authorization = Credentials.basic("d842cdda9617448b980e7eeceeff0288", "NWTgHV5iy2EDz2uzoRJ8UbDEUoeO0mhujUWPLBMd");
        Request request = new Request.Builder()
                .post(formBody)
                .url("https://login.eveonline.com/v2/oauth/token/")
                .addHeader("Authorization", authorization)
                .build();
        try {
            String json = new OkHttpClient().newCall(request).execute().body().string();
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public JsonNode getRole(Long id, String accessToken) throws SeaTideException {
        Request request = new Request.Builder().get().url("https://esi.evetech.net/latest/characters/" + id + "/roles/?datasource=tranquility&token=" + accessToken).build();
        try {
            String json = new OkHttpClient().newCall(request).execute().body().string();
            return new ObjectMapper().readTree(json);
        } catch (HttpClientErrorException e) {
            throw SeaTideException.with(StatusEnumeration.F2);
        } catch (IOException e) {
            throw SeaTideException.with(StatusEnumeration.F1);
        }
    }

    public Integer sendVerificationCode(String email) {
        Integer verificationCode = generateVerificationCode();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("service@cciradih.top");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Ranger Regiment 海潮系统");
        simpleMailMessage.setText("角色组验证码：" + verificationCode);
        javaMailSender.send(simpleMailMessage);
        return verificationCode;
    }

    private Integer generateVerificationCode() {
        StringBuilder verificationCodeStringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int bit = new Random().nextInt(10);
            verificationCodeStringBuilder.append(bit);
        }
        String verificationCodeString = verificationCodeStringBuilder.toString();
        return Integer.valueOf(verificationCodeString);
    }
}
