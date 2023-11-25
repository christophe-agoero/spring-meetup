package fr.agoero.ws.controller;

import fr.agoero.ws.dto.request.CharacterCreateDto;
import fr.agoero.ws.dto.response.CharacterDto;
import fr.agoero.core.service.CharacterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;

import static fr.agoero.core.domain.Character.CODE_REGEX;
import static fr.agoero.core.exception.common.ValidationConstants.PATTERN;
import static fr.agoero.ws.mapper.CharacterMapper.buildDtoFromEntity;
import static fr.agoero.ws.mapper.CharacterMapper.buildEntityFromCreateDto;
import static fr.agoero.ws.util.UrlConstants.*;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@Validated
@RequestMapping(
        value = CHARACTERS_PATH,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping(value = CHARACTERS_BY_ID_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterDto> findById(@PathVariable int characterId) {
        return ResponseEntity.ok(buildDtoFromEntity(characterService.findById(characterId)));
    }

    @GetMapping(value = CHARACTERS_BY_CODE_PATH)
    public ResponseEntity<Void> findByCode(@PathVariable @Pattern(regexp = CODE_REGEX, message = PATTERN)  String code) {
        // peu importe, c'est juste pour la gestion de l'exception
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAuthority('LOUIS_BMX_11')")
    public ResponseEntity<Void> create(@Valid @RequestBody CharacterCreateDto characterCreateDto) {
        var character = characterService.create(buildEntityFromCreateDto(characterCreateDto));
        return ResponseEntity.created(
                URI.create(
                        requireNonNull(
                                fromMethodCall(
                                        on(this.getClass()).findById(character.getId())
                                ).build()
                                 .getPath()
                        )
                )
        ).build();
    }

}


