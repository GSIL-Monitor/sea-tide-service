package top.cciradih.sea_tide_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.cciradih.sea_tide_service.service.AllianceService;
import top.cciradih.sea_tide_service.view.AllianceView;
import top.cciradih.sea_tide_service.view.ResponseView;

@CrossOrigin("*")
@RestController
@RequestMapping("/alliances")
public class AllianceController {
    @Autowired
    private AllianceService allianceService;

    @PostMapping("/")
    public ResponseEntity<ResponseView> save(@RequestBody AllianceView allianceView) {
        return allianceService.save(allianceView);
    }

    @GetMapping("/administrator/{characterId}/tax/")
    public ResponseEntity<ResponseView> getTax(@PathVariable Long characterId, @RequestParam Long startDate, @RequestParam Long endDate) {
        return allianceService.getTax(characterId, startDate, endDate);
    }
}
