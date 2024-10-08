package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.ChangePasswordRequest;
import br.com.ero.ecommerce.dto.EmailConfirmationRequest;
import br.com.ero.ecommerce.dto.LoginRequest;
import br.com.ero.ecommerce.exception.ResourceNotFoundException;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.service.JwtService;
import br.com.ero.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );
    final UserDetails userDetails = userService.getUserByEmail(loginRequest.getEmail());
    final String jwt = jwtService.generateToken(userDetails);

    return ResponseEntity.ok(jwt);
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Valid User user) {
    return ResponseEntity.ok(userService.registerUser(user));
  }

  @PostMapping("/change-password")
  public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    userService.changePassword(email, request);
    return ResponseEntity.ok().body("Password changed");
  }

  @PostMapping("/confirm-email")
  public ResponseEntity<?> confirmEmail(@RequestBody EmailConfirmationRequest request) {
    try {
      userService.confirmEmail(request.getEmail(), request.getConfirmationCode());
      return ResponseEntity.ok().body("Email confirmed successfully");
    } catch (BadCredentialsException e) {
      return ResponseEntity.badRequest().body("Invalid confirmation Code");
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
