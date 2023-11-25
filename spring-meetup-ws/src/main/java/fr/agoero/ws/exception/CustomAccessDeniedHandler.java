package fr.agoero.ws.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.net.URI;
import java.util.LinkedHashMap;

import static fr.agoero.core.exception.common.CustomErrorResponseUtil.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Custom accessDenied pour ajouter ProblemDetail en body
 *
 * @see org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final String ACCESS_DENIED_DEFAULT_DETAIL = "access denied";

    @Setter
    private String realmName;

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        var parameters = new LinkedHashMap<String, String>();
        if (this.realmName != null) {
            parameters.put("realm", this.realmName);
        }
        try {
            fillResponse(response, buildProblemDetail(
                            FORBIDDEN,
                            ACCESS_DENIED_DEFAULT_DETAIL,
                            null,
                            null,
                            URI.create(request.getRequestURI())
                    )
            );
        } catch (Exception e) {
            // base response without body
            log.warn("Error during add body for accessDeniedException", e);
        }

        var wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(FORBIDDEN.value());
    }

}
