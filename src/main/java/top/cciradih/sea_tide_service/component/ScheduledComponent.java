package top.cciradih.sea_tide_service.component;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.entity.Corporation;
import top.cciradih.sea_tide_service.entity.Tax;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.repository.CorporationRepository;
import top.cciradih.sea_tide_service.repository.TaxRepository;

import java.util.*;
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
    private TaxRepository taxRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void getTax() throws SeaTideException {
        JsonNode corporationIdsJsonNode = allianceComponent.getCorporation();
        List<Tax> taxList = new ArrayList<>();
        for (JsonNode corporationIdJsonNode : corporationIdsJsonNode) {
            Long corporationId = corporationIdJsonNode.asLong();
            boolean exist = corporationRepository.existsById(corporationId);
            if (exist) {
                Supplier<SeaTideException> seaTideExceptionSupplier = () -> SeaTideException.with(StatusEnumeration.F3);
                Corporation corporation = corporationRepository.findById(corporationId).orElseThrow(seaTideExceptionSupplier);
                Long ceoId = corporation.getCeoId();
                exist = characterRepository.existsById(ceoId);
                if (exist) {
                    Character character = characterRepository.findById(ceoId).orElseThrow(seaTideExceptionSupplier);
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
                                Tax tax = new Tax(walletJournalId, amount, date, characterId, corporationId);
                                taxList.add(tax);
                            }
                        }
                    }
                }
            }
        }
        List<Tax> existTaxList = taxRepository.findAll();
        HashSet<Tax> existTaxSet = new HashSet<>(existTaxList);
        taxList = new LinkedList<>(taxList);
        taxList.removeAll(existTaxSet);
        taxRepository.saveAll(taxList);
    }
}
