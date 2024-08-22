package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.OrderDTO;
import br.com.ero.ecommerce.model.Order;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping()
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<OrderDTO>> getAllOrders() {
    List<OrderDTO> orders = orderService.getAllOrders();

    return ResponseEntity.ok(orders);
  }

  @GetMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<OrderDTO>> geUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
    Long userId = ((User) userDetails).getId();
    List<OrderDTO> orders = orderService.getUserOrders(userId);

    return ResponseEntity.ok(orders);
  }

  @PutMapping("/{orderId}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<OrderDTO> updateOrdersStatus(@PathVariable Long orderId,
                                                     @RequestParam Order.OrderStatus status) {
    OrderDTO updateOrder = orderService.updateOrderStatus(orderId, status);

    return ResponseEntity.ok(updateOrder);
  }
}
