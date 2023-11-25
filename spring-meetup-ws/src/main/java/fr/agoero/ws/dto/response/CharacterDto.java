package fr.agoero.ws.dto.response;

public record CharacterDto(
        int id,

        String firstname,

        String lastname,

        String code
) {
}
