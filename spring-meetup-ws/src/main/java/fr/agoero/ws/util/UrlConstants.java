package fr.agoero.ws.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("squid:S1075")
public final class UrlConstants {

    // URL
    public static final String CHARACTERS_PATH = "/characters";
    public static final String CHARACTERS_BY_ID_PATH ="/{characterId}";
    public static final String CHARACTERS_BY_CODE_PATH ="/code/{code}";

}

