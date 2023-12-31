package fr.agoero.ws.mapper;

import fr.agoero.core.domain.Character;
import fr.agoero.ws.dto.request.CharacterCreateDto;
import fr.agoero.ws.dto.response.CharacterDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe utilitaire de mapping entre Character et CharacterDto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterMapper {

    /**
     * Permet de construire un CharacterDto à partir d'un Character
     *
     * @param character
     * @return CharacterDto
     */
    public static CharacterDto buildDtoFromEntity(Character character) {
        return new CharacterDto(
                character.getId(),
                character.getFirstname(),
                character.getLastname(),
                character.getCode()
        );
    }

    /**
     * Permet de construire un Character à partir d'un CreateCharacterDto
     *
     * @param characterCreateDto
     * @return Character
     */
    public static Character buildEntityFromCreateDto(CharacterCreateDto characterCreateDto) {
        return Character.builder()
                        .firstname(characterCreateDto.firstname())
                        .lastname(characterCreateDto.lastname())
                        .code(characterCreateDto.code())
                        .build();
    }

}
