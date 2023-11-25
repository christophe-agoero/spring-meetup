package fr.agoero.core.exception;

import fr.agoero.core.domain.Character;
import fr.agoero.core.exception.common.ExceptionConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

import static fr.agoero.core.exception.common.ExceptionConstants.*;

/**
 * Classe de constante pour les exceptions Character
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterExceptionConstants {

    private static final String RESOURCE_NAME = Character.class.getSimpleName();

    // not found
    public static final URI CHARACTER_NOT_FOUND_TYPE = buildNotFoundType(RESOURCE_NAME);
    public static final String CHARACTER_NOT_FOUND_TITLE = buildNotFoundTitle(RESOURCE_NAME);
    public static final String CHARACTER_NOT_FOUND_DETAIL = "Le personnage avec l'identifiant %d n'a pas été trouvé";
    // already exist
    public static final URI CHARACTER_ALREADY_EXISTS_TYPE = buildAlreadyExistType(RESOURCE_NAME);
    public static final String CHARACTER_ALREADY_EXISTS_TITLE = buildAlreadyExistTitle(RESOURCE_NAME);
    public static final String CHARACTER_ALREADY_EXISTS_DETAIL = "Le personnage avec le code %s existe déjà";
    // constraint violation
    public static final URI CHARACTER_CONSTRAINT_VIOLATION_TYPE = buildConstraintViolationType(RESOURCE_NAME);
    public static final String CHARACTER_CONSTRAINT_VIOLATION_TITLE = buildConstraintViolationTitle(RESOURCE_NAME);

}