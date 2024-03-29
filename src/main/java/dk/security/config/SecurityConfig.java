package dk.security.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import dk.security.error.CustomOAuth2AccessDeniedHandler;
import dk.security.error.CustomOAuth2AuthenticationEntryPoint;
import dk.security.filter.TokenValidationFilter;
import dk.security.service.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

@Configuration
public class SecurityConfig {

  @Autowired
  private TokenStore tokenStore;

  @Value("${app.secret-key}")
  private String tokenSecret;

  @Bean
  public TokenValidationFilter tokenValidationFilter() {
    return new TokenValidationFilter(tokenStore);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    http
            // Registers the custom TokenValidationFilter to be executed before the UsernamePasswordAuthenticationFilter.
            // This ensures that the token validation logic is applied to every request before attempting authentication.
            .addFilterBefore(tokenValidationFilter(), UsernamePasswordAuthenticationFilter.class)
            .cors(Customizer.withDefaults()) //Will use the CorsConfigurationSource bean declared in CorsConfig.java
            .csrf(csrf -> csrf.disable())  //We can disable csrf, since we are using token based authentication, not cookie based
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer((oauth2ResourceServer) ->
                    oauth2ResourceServer
                            .jwt((jwt) -> jwt.decoder(jwtDecoder())
                                    .jwtAuthenticationConverter(authenticationConverter())
                            )
                            .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomOAuth2AccessDeniedHandler()));

    http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/auth/login")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/auth/logout")).authenticated()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/user-with-role")).permitAll() //Clients can create a user for themself

            //This is for demo purposes only, and should be removed for a real system
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/demo/anonymous")).permitAll()

            //Allow index.html for anonymous users
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/index.html")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/")).permitAll()

            // Allow for database overiew html
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/database-overview.html")).permitAll()

            //Allow for swagger-ui
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-ui/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-resources/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/v3/api-docs/**")).permitAll()

            // Allow for ADMIN to POST, PUT, and DELETE Cinemas, and ALL to GET
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cinemas")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cinemas/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/cinemas")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/cinemas/*")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/cinemas/*")).hasAuthority("ADMIN")

            // Allow for ADMIN to POST, PUT, and DELETE Halls, and ALL to GET
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/halls")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/halls/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/halls")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/halls/*")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/halls/*")).hasAuthority("ADMIN")

            // Allow for ADMIN to POST, PUT, and DELETE Movies, and ALL to GET
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/movies")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/movies/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/movies")).hasAnyAuthority("ADMIN", "EMPLOYEE")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/movies/*")).hasAnyAuthority("ADMIN", "EMPLOYEE")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/movies/*")).hasAnyAuthority("ADMIN", "EMPLOYEE")

            // Allow for ADMIN to POST, PUT, and DELETE Schedules, and ALL to GET
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/schedules")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/schedules/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/schedules")).hasAnyAuthority("ADMIN", "EMPLOYEE")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/schedules/*")).hasAnyAuthority("ADMIN", "EMPLOYEE")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/schedules/*")).hasAnyAuthority("ADMIN", "EMPLOYEE")

            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/reservations")).hasAnyAuthority("USER")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/reservations/prices")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/reservations/schedules/**")).permitAll()

            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/prices/**")).hasAnyAuthority("ADMIN", "EMPLOYEE")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/prices/**")).hasAnyAuthority("ADMIN", "EMPLOYEE")

            //Required for error responses
            .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll());

            //This is for demo purposes only, and should be removed for a real system
            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/user-only")).hasAuthority("USER")
            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/admin-only")).hasAuthority("ADMIN")

            //Use this to completely disable security (Will not work if endpoints has been marked with @PreAuthorize)
            //.requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll());
            //.anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationConverter authenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  public SecretKey secretKey() {
    return new SecretKeySpec(tokenSecret.getBytes(), "HmacSHA256");
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(secretKey()).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey()));
  }

}
