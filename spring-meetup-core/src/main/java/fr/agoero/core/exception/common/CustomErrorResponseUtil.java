package fr.agoero.core.exception.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomErrorResponseUtil {

    public static final String PROBLEM_DETAIL_TIMESTAMP_PROPERTY = "timestamp";
    private static final ObjectMapper JSON_MAPPER = new Jackson2ObjectMapperBuilder().build();

    public static void addTimestamp(ProblemDetail problemDetail) {
        problemDetail.setProperty(PROBLEM_DETAIL_TIMESTAMP_PROPERTY, getTimestamp());
    }

    public static ProblemDetail buildProblemDetail(HttpStatus httpStatus, String detail, URI type, String title, URI instance) {
        var problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        problemDetail.setTitle(title);
        if (type != null) {
            problemDetail.setType(type);
        }
        problemDetail.setInstance(instance);
        addTimestamp(problemDetail);
        return problemDetail;
    }

    public static String getTimestamp() {
        return ZonedDateTime.now()
                .withZoneSameInstant(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
        var wwwAuthenticateBuilder = new StringBuilder();
        wwwAuthenticateBuilder.append("Bearer");
        if (!parameters.isEmpty()) {
            wwwAuthenticateBuilder.append(" ");
            int i = 0;

            for (var var3 = parameters.entrySet().iterator(); var3.hasNext(); ++i) {
                Map.Entry<String, String> entry = var3.next();
                wwwAuthenticateBuilder.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                if (i != parameters.size() - 1) {
                    wwwAuthenticateBuilder.append(", ");
                }
            }
        }
        return wwwAuthenticateBuilder.toString();
    }

    public static void fillResponse(HttpServletResponse response, ProblemDetail problemDetail) throws IOException {
            response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);
            response.getWriter().write(JSON_MAPPER.writeValueAsString(problemDetail));
    }

}
