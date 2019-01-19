package top.cciradih.sea_tide_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.cciradih.sea_tide_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
