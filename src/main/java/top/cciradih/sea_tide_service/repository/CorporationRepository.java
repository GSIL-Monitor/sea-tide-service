package top.cciradih.sea_tide_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.cciradih.sea_tide_service.entity.Corporation;

public interface CorporationRepository extends JpaRepository<Corporation, Long> {
}
