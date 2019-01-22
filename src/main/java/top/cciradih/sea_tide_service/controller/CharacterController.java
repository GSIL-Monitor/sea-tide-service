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
}
