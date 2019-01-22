package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.entity.Corporation;
import top.cciradih.sea_tide_service.entity.CorporationTax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.repository.CorporationRepository;
import top.cciradih.sea_tide_service.repository.CorporationTaxRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Component
public class ScheduledComponent {
    @Autowired
    private AllianceComponent allianceComponent;
    @Autowired
    private CorporationComponent corporationComponent;
    @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterComponent characterComponent;
    @Autowired
    private SeaTideComponent seaTideComponent;
    @Autowired
    private CorporationTaxRepository corporationTaxRepository;

    //    @Scheduled(cron = "0 0 * * * *")
    public void say() throws SeaTideException {
        JsonNode corporationIdJsonNode = allianceComponent.getCorporation();
        List<CorporationTax> corporationTaxList = new ArrayList<>();
        for (JsonNode corporationJsonNode : corporationIdJsonNode) {
            Long corporationId = corporationJsonNode.asLong();
            boolean exist = corporationRepository.existsById(corporationId);
            if (exist) {
                Supplier<SeaTideException> seaTideExceptionSupplier = () -> SeaTideException.with(StatusEnumeration.F3);
                Corporation corporation = corporationRepository.findById(corporationId).orElseThrow(seaTideExceptionSupplier);
                Long ceoId = corporation.getCeoId();
                exist = characterRepository.existsById(ceoId);
                if (exist) {
                    Character character = characterRepository.findById(ceoId).orElseThrow(seaTideExceptionSupplier);
                    System.out.println(character);
                    String refreshToken = character.getRefreshToken();
                    JsonNode tokenJsonNode = characterComponent.refreshToken(refreshToken);
                    String accessToken = tokenJsonNode.path("access_token").asText();
                    List<JsonNode> walletJournalJsonNodeList = new ArrayList<>();
                    walletJournalJsonNodeList = corporationComponent.getWalletJournal(corporationId, 1, accessToken, walletJournalJsonNodeList);
                    for (JsonNode walletJournalJsonNode : walletJournalJsonNodeList) {
                        for (JsonNode walletJournal : walletJournalJsonNode) {
                            String refType = walletJournal.path("ref_type").asText();
                            boolean bountyPrizes = "bounty_prizes".equals(refType);
                            if (bountyPrizes) {
                                Long walletJournalId = walletJournal.path("id").asLong();
                                Long amount = walletJournal.path("amount").asLong();
                                String dateString = walletJournal.path("date").asText();
                                Date date = seaTideComponent.format(dateString);
                                Long characterId = walletJournal.path("second_party_id").asLong();
                                CorporationTax corporationTax = new CorporationTax(walletJournalId, amount, date, characterId, corporationId);
                                corporationTaxList.add(corporationTax);
                            }
                        }
                    }
                    System.out.println(corporationTaxList);
                }
            }
        }
        corporationTaxRepository.saveAll(corporationTaxList);
    }
}
