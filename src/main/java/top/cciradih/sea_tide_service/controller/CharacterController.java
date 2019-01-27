package top.cciradih.sea_tide_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.cciradih.sea_tide_service.service.CharacterService;
import top.cciradih.sea_tide_service.view.CharacterView;
import top.cciradih.sea_tide_service.view.ResponseView;

@CrossOrigin("*")
@RestController
@RequestMapping("/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @GetMapping("/id/")
    public ResponseEntity<ResponseView> getId(@RequestParam String code) {
        return characterService.getId(code);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseView> save(@RequestBody CharacterView characterView) {
        return characterService.save(characterView);
    }

    @GetMapping("/{characterId}/tax/")
    public ResponseEntity<ResponseView> getTax(@PathVariable Long characterId, @RequestParam Long startDate, @RequestParam Long endDate) {
        return characterService.getTax(characterId, startDate, endDate);
    }

    @GetMapping("/{characterId}/verification-code/")
    public ResponseEntity<ResponseView> getVerificationCode(@PathVariable Long characterId, @RequestParam String email) {
        return characterService.getVerificationCode(characterId, email);
    }

    @GetMapping("/{characterId}/verification/")
    public ResponseEntity<ResponseView> verify(@PathVariable Long characterId, @RequestParam Integer verificationCode, @RequestParam String nickname) {
        return characterService.verify(characterId, verificationCode, nickname);
    }

    @GetMapping("/{characterId}/group/")
    public ResponseEntity<ResponseView> getGroup(@PathVariable Long characterId) {
        return characterService.getGroup(characterId);
    }
}
