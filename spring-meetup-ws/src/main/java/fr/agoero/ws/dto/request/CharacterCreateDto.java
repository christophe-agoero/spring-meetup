package fr.agoero.ws.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static fr.agoero.core.domain.Character.CODE_REGEX;
import static fr.agoero.core.exception.common.ValidationConstants.NOT_BLANK;
import static fr.agoero.core.exception.common.ValidationConstants.PATTERN;

@Valid
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterCreateDto {


    private String firstname;

    @JsonProperty("surname")
    @NotBlank
    private String lastname;

    @NotBlank(message = NOT_BLANK)
    //@Pattern(regexp = CODE_REGEX, message = PATTERN)
    private String code;

    private boolean visible;

}
