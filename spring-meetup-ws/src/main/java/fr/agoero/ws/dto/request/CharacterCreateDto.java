package fr.agoero.ws.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static fr.agoero.core.domain.Character.CODE_REGEX;
import static fr.agoero.core.exception.common.ValidationConstants.NOT_BLANK;
import static fr.agoero.core.exception.common.ValidationConstants.PATTERN;

@Valid
public record CharacterCreateDto(
        String firstname,

        @JsonProperty("surname")
        @NotBlank
        String lastname,

        @NotBlank(message = NOT_BLANK)
        @Pattern(regexp = CODE_REGEX, message = PATTERN)
        String code,

        boolean visible
) {
}
