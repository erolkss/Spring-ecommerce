package br.com.ero.ecommerce.dto;

import br.com.ero.ecommerce.model.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
  private Long id;
  private Long userId;
  private String address;
  private String phoneNumber;
  private Order.OrderStatus status;
  private LocalDateTime createdAt;
  private List<OrderItemDTO> orderItems;
}
