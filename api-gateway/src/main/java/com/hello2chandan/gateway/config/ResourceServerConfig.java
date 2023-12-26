package com.hello2chandan.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeExchange(t -> t  
                .pathMatchers(HttpMethod.GET, "/api/1.0/student/**")
                            .hasAuthority("SCOPE_read")
                            /* .anyExchange()
                            .hasRole("ROLE_user")*/
                .pathMatchers(HttpMethod.PUT, "/api/1.0/student/**")
                            .hasAuthority("SCOPE_write")
                            /*.anyExchange()
                            .hasRole("ROLE_admin") */
                .pathMatchers(HttpMethod.GET, "/api/1.0/school/**")
                            .hasAuthority("SCOPE_read")
                            /*.anyExchange()
                            .hasRole("ROLE_user") */
                .pathMatchers(HttpMethod.POST, "/api/1.0/school/**")
                            .hasAuthority("SCOPE_write")
                            /*.anyExchange()
                            .hasRole("ROLE_admin") */
                .pathMatchers(HttpMethod.GET, "/api/1.0/payment/**")
                            .hasAuthority("SCOPE_read")
                            /*.anyExchange()
                            .hasRole("ROLE_user") */
                .pathMatchers(HttpMethod.POST, "/api/1.0/payment/**")
                            .hasAuthority("SCOPE_write")
                            /*.anyExchange()
                            .hasRole("ROLE_admin") */
                .anyExchange()
                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));

        return http.build();

        /*
         * http.authorizeExchange(t -> t.pathMatchers("/**")
         * .authenticated())
         * .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
         * jwt.jwtDecoder(jwtDecoder())));
         * 
         * return http.build();
         */

    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}
