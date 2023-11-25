package fr.agoero.core.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static fr.agoero.core.exception.CharacterExceptionConstants.*;
import static org.springframework.http.HttpStatus.*;


/**
 * Enumeration des réponses d'erreurs
 */
@AllArgsConstructor
@Getter
public enum ErrorResponseExceptionEnum {

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR_RESPONSE(INTERNAL_SERVER_ERROR, null, null),

    // CHARACTER
    // not found
    CHARACTER_NOT_FOUND(NOT_FOUND, CHARACTER_NOT_FOUND_TYPE, CHARACTER_NOT_FOUND_TITLE),
    // already exists
    CHARACTER_ALREADY_EXISTS(CONFLICT, CHARACTER_ALREADY_EXISTS_TYPE, CHARACTER_ALREADY_EXISTS_TITLE),
    // constraint violation
    CHARACTER_CONSTRAINT_VIOLATION(BAD_REQUEST, CHARACTER_CONSTRAINT_VIOLATION_TYPE, CHARACTER_CONSTRAINT_VIOLATION_TITLE),
    ;

    private final HttpStatus httpStatus;
    private final URI type;
    private final String title;

}