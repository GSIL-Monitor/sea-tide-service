package top.cciradih.sea_tide_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cciradih.sea_tide_service.entity.Character;
import top.cciradih.sea_tide_service.repository.CharacterRepository;
import top.cciradih.sea_tide_service.view.CharacterView;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    public CharacterView save(CharacterView characterView) {
        String name = characterView.getName();
        Character character = new Character();
        character.setName(name);
        character = characterRepository.save(character);
        Long id = character.getId();
        characterView.setId(id);
        return characterView;
    }
}
