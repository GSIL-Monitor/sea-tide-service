package top.cciradih.sea_tide_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.entity.Role;
import top.cciradih.sea_tide_service.repository.RoleRepository;
import top.cciradih.sea_tide_service.view.RoleView;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleView save(RoleView roleView) {
        String name = roleView.getName();
        Role role = new Role();
        role.setName(name);
        role = roleRepository.save(role);
        Long id = role.getId();
        roleView.setId(id);
        return roleView;
    }
}
