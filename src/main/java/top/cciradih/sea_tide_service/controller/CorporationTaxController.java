package top.cciradih.sea_tide_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.cciradih.sea_tide_service.component.ScheduledComponent;
import top.cciradih.sea_tide_service.exception.SeaTideException;
import top.cciradih.sea_tide_service.service.CorporationTaxService;
import top.cciradih.sea_tide_service.view.ResponseView;

@CrossOrigin("*")
@RestController
@RequestMapping("/corporations/tax")
public class CorporationTaxController {
    @Autowired
    private CorporationTaxService corporationTaxService;
    @Autowired
    private ScheduledComponent scheduledComponent;

    @GetMapping("/{characterId}/")
    public ResponseEntity<ResponseView> get(@PathVariable Long characterId, @RequestParam Long startDate, @RequestParam Long endDate) {
        return corporationTaxService.get(characterId, startDate, endDate);
    }

    @GetMapping("/")
    public void get() {
        try {
            scheduledComponent.say();
        } catch (SeaTideException e) {
            e.printStackTrace();
        }
    }
}
