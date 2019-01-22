package top.cciradih.sea_tide_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.cciradih.sea_tide_service.service.CorporationService;
import top.cciradih.sea_tide_service.view.CorporationView;
import top.cciradih.sea_tide_service.view.ResponseView;

@CrossOrigin("*")
@RestController
@RequestMapping("/corporations")
public class CorporationController {
    @Autowired
    private CorporationService corporationService;

    @PostMapping("/")
    public ResponseEntity<ResponseView> save(@RequestBody CorporationView corporationView) {
        return corporationService.save(corporationView);
    }
}