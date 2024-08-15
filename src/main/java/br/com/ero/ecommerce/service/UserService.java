package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private User registerUser(User user) {
    Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

    if (optionalUser.isPresent()) {
      throw new IllegalStateException("Email already taken");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(User.Role.USER);

    return userRepository.save(user);
  }
}
