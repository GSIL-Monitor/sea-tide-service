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
import top.cciradih.sea_tide_service.service.RoleService;
import top.cciradih.sea_tide_service.view.ResponseView;
import top.cciradih.sea_tide_service.view.RoleView;

@RestController
@RequestMapping("/characters")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResponseFormatComponent responseFormatComponent;

    @PostMapping("/")
    public ResponseEntity<ResponseView> save(@RequestBody RoleView roleView) {
        roleView = roleService.save(roleView);
        return responseFormatComponent.format(StatusEnumeration.S0, HttpStatus.OK, roleView);
    }
}
