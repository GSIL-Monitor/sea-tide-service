package top.cciradih.sea_tide_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.cciradih.sea_tide_service.entity.Character;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByEmailAndNicknameAndVerification(String email, String nickname, Boolean verification);
}
