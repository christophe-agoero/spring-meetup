package fr.agoero.ws.config;

import fr.agoero.ws.exception.CustomAccessDeniedHandler;
import fr.agoero.ws.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static fr.agoero.ws.util.UrlConstants.CHARACTERS_PATH;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity(proxyTargetClass = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/error").permitAll()
                    //.requestMatchers(CHARACTERS_PATH).hasAuthority("LOUIS_BMX_11")
                            .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .jwt(withDefaults())
            )
        ;
        return http.build();
    }

}
