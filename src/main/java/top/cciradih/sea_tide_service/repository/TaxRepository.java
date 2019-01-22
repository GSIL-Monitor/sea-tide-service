package top.cciradih.sea_tide_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.cciradih.sea_tide_service.entity.Tax;

import java.util.Date;
import java.util.List;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    List<Tax> findByDateBetween(Date start, Date end);

    List<Tax> findByCharacterIdAndDateBetween(Long characterId, Date start, Date end);

    List<Tax> findByCorporationIdAndDateBetween(Long corporationId, Date start, Date end);
}
