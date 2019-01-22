package top.cciradih.sea_tide_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.cciradih.sea_tide_service.entity.CorporationTax;

import java.util.Date;
import java.util.List;

public interface CorporationTaxRepository extends JpaRepository<CorporationTax, Long> {
    List<CorporationTax> findByCharacterIdAndDateBetween(Long characterId, Date start, Date end);
}
