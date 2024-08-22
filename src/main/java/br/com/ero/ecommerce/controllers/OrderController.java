package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.OrderDTO;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.service.OrderService;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping()
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<OrderDTO> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam String address,
                                              @RequestParam String phoneNumber) {
    Long userId = ((User) userDetails).getId();
    OrderDTO orderDTO = orderService.createOrder(userId, address, phoneNumber);

    return ResponseEntity.ok(orderDTO);
  }

}
