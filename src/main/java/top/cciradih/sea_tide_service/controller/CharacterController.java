package top.cciradih.sea_tide_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cciradih.sea_tide_service.component.ResponseFormatComponent;
import top.cciradih.sea_tide_service.enumeration.StatusEnumeration;
import top.cciradih.sea_tide_service.service.CharacterService;
import top.cciradih.sea_tide_service.view.CharacterView;
import top.cciradih.sea_tide_service.view.ResponseView;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    @PostMapping("/")
    public ResponseEntity<ResponseView> save(@RequestBody CharacterView characterView) {
        characterView = characterService.save(characterView);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, characterView);
    }
}
