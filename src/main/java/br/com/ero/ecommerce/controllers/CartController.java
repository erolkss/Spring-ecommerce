package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.CartDTO;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping("/add")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<CartDTO> addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity) {
    Long userId = ((User) userDetails).getId();

    return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity));
  }
}
