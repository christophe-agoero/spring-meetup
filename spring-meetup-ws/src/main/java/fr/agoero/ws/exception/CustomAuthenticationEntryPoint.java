package fr.agoero.ws.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.LinkedHashMap;

import static fr.agoero.core.exception.common.CustomErrorResponseUtil.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Custom entryPoint pour ajouter ProblemDetail en body
 *
 * @see org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR = "error";
    private static final String ERROR_DESCRIPTION = "error_description";
    private static final String ERROR_URI = "error_uri";

    public static final String AUTHENTICATION_DEFAULT_DETAIL = "authentication error";
    @Setter
    private String realmName;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        var status = UNAUTHORIZED;
        var parameters = new LinkedHashMap<String, String>();
        if (this.realmName != null) {
            parameters.put("realm", this.realmName);
        }

        if (authException instanceof OAuth2AuthenticationException authenticationException) {
            status = getHttpStatus(request, response, status, parameters, authenticationException);
        } else {
            try {
                fillResponse(response, buildProblemDetail(
                                UNAUTHORIZED,
                                AUTHENTICATION_DEFAULT_DETAIL,
                                null,
                                null,
                                URI.create(request.getRequestURI())
                        )
                );
            } catch (Exception e) {
                // base response without body
                log.warn("Error during add body for authenticationException", e);
            }
        }
        String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);

        response.setStatus(status != null ? status.value() : UNAUTHORIZED.value()); // NOSONAR
    }

    private static HttpStatus getHttpStatus(HttpServletRequest request, HttpServletResponse response, HttpStatus status, LinkedHashMap<String, String> parameters, OAuth2AuthenticationException authenticationException) {
        var error = authenticationException.getError();
        parameters.put(ERROR, error.getErrorCode());
        if (StringUtils.hasText(error.getDescription())) {
            parameters.put(ERROR_DESCRIPTION, error.getDescription());
        }

        if (StringUtils.hasText(error.getUri())) {
            parameters.put(ERROR_URI, error.getUri());
        }

        if (error instanceof BearerTokenError bearerTokenError) {
            if (StringUtils.hasText(bearerTokenError.getScope())) {
                parameters.put("scope", bearerTokenError.getScope());
            }
            status = ((BearerTokenError) error).getHttpStatus();
        }
        try {
            fillResponse(response, buildProblemDetail(
                            status != null ? status : UNAUTHORIZED,
                            parameters.get(ERROR_DESCRIPTION),
                            URI.create(parameters.get(ERROR_URI)),
                            parameters.get(ERROR),
                            URI.create(request.getRequestURI())
                    )
            );
        } catch (Exception e) {
            // base response without body
            log.warn("Error during add body for authenticationException", e);
        }
        return status;
    }

}
