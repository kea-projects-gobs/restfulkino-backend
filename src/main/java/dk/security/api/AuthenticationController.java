package dk.security.api;

import dk.security.dto.LoginRequest;
import dk.security.dto.LoginResponse;
import dk.security.entity.UserWithRoles;
import dk.security.service.TokenStore;
import dk.security.service.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {


  @Value("${app.token-issuer}")
  private String tokenIssuer;

  @Value("${app.token-expiration}")
  private long tokenExpiration;

  private TokenStore tokenStore;
  private AuthenticationManager authenticationManager;

  JwtEncoder encoder;

  public AuthenticationController(AuthenticationConfiguration authenticationConfiguration, JwtEncoder encoder, TokenStore tokenStore) throws Exception {
    this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    this.encoder = encoder;
    this.tokenStore = tokenStore;
  }

  @PostMapping("login")
  @Operation(summary = "Login", description = "Use this to login and get a token")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

    try {
      UsernamePasswordAuthenticationToken uat = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      //The authenticate method will use the loadUserByUsername method in UserDetailsServiceImp
      Authentication authentication = authenticationManager.authenticate(uat);

      UserWithRoles user = (UserWithRoles) authentication.getPrincipal();
      Instant now = Instant.now();
      long expiry = tokenExpiration;
      String scope = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(joining(" "));

      JwtClaimsSet claims = JwtClaimsSet.builder()
              .issuer(tokenIssuer)  //Only this for simplicity
              .issuedAt(now)
              .expiresAt(now.plusSeconds(tokenExpiration))
              .subject(user.getUsername())
              .claim("roles", scope)
              .build();
      JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
      String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
      // Mark the token as valid in TokenStore
      tokenStore.storeToken(token, true);
      List<String> roles = user.getRoles().stream().map(role -> role.getRoleName()).toList();
      return ResponseEntity.ok()
              .body(new LoginResponse(user.getUsername(), token, roles));

    } catch (BadCredentialsException | AuthenticationServiceException e) {
      // AuthenticationServiceException is thrown if the user is not found
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UserDetailsServiceImp.WRONG_USERNAME_OR_PASSWORD);
    }
  }

  /**
   * Logout the user and invalidates the token.
   * <p>
   * This method is used to invalidate the token provided in the Authorization header of the request.
   * </p>
   * <p>
   * If the token is valid, it is removed from the token store and the user is logged out.
   * <br>
   * If the token is invalid or expired, the method will still return a successful response.
   * </p>
   * @param authHeader The Authorization header of the request.
   * @return A response entity with a 200 OK status code.
   */
  @PostMapping("/logout")
  @Operation(summary = "Logout", description = "Used to invalidate token on logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
          String token = authHeader.substring(7);
          // Attempt to invalidate the token if it exists, but don't error if the token is invalid or expired
          tokenStore.invalidateToken(token);
      }
      // Always return a successful response to ensure the client can "log out"
      return ResponseEntity.ok().build();
  }
}
