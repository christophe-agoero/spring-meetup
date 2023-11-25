package fr.agoero.core.service;

import fr.agoero.core.domain.Character;
import fr.agoero.core.exception.CharacterExceptionConstants;
import fr.agoero.core.exception.common.CustomErrorResponseException;
import fr.agoero.core.exception.common.ErrorResponseExceptionEnum;
import fr.agoero.core.repository.CharacterRepository;
import fr.agoero.core.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static fr.agoero.core.exception.CharacterExceptionConstants.CHARACTER_ALREADY_EXISTS_DETAIL;
import static fr.agoero.core.exception.CharacterExceptionConstants.CHARACTER_NOT_FOUND_DETAIL;
import static fr.agoero.core.exception.common.ErrorResponseExceptionEnum.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

    private final CharacterRepository characterRepository;

    /**
     * Méthode permettant de retourner un personnage par son identifiant
     *
     * @param id
     * @return un personnage
     */
    public Character findById(Integer id) {
        return characterRepository.findById(id).orElseThrow(
                () -> new CustomErrorResponseException(
                        CHARACTER_NOT_FOUND,
                        String.format(CHARACTER_NOT_FOUND_DETAIL, id)
                )
        );
    }

    /**
     * Méthode permettant de créer un personnage
     *
     * @param character
     * @return character
     */
    @Transactional
    public Character create(Character character) {
        ValidationUtil.checkObjectAndThrow(CHARACTER_CONSTRAINT_VIOLATION, character);
        if(characterRepository.existsByCode(character.getCode())){
            throw new CustomErrorResponseException(
                    CHARACTER_ALREADY_EXISTS,
                    character.getCode()
            );
        }
        return characterRepository.persist(character);
    }

}
