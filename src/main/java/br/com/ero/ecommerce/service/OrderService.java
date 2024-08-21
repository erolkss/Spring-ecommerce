package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.dto.CartDTO;
import br.com.ero.ecommerce.dto.OrderDTO;
import br.com.ero.ecommerce.exception.InsufficientStockException;
import br.com.ero.ecommerce.exception.ResourceNotFoundException;
import br.com.ero.ecommerce.mapper.CartMapper;
import br.com.ero.ecommerce.mapper.OrderMapper;
import br.com.ero.ecommerce.model.*;
import br.com.ero.ecommerce.repositories.OrderRepository;
import br.com.ero.ecommerce.repositories.ProductRepository;
import br.com.ero.ecommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

  private final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final OrderMapper orderMapper;
  private final CartMapper cartMapper;

  @Transactional
  public OrderDTO createOrder(Long userId, String address, String phoneNumber) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    CartDTO cartDTO = cartService.getCart(userId);
    Cart cart = cartMapper.toEntity(cartDTO);

    if (cart.getItems().isEmpty()) {
      throw new IllegalStateException("Cannot create a order with an empty cart");
    }

    Order order = new Order();
    order.setUser(user);
    order.setAddress(address);
    order.setPhoneNumber(phoneNumber);
    order.setStatus(Order.OrderStatus.PREPARING);
    order.setCreatedAt(LocalDateTime.now());

    List<OrderItem> orderItems = createOrderItems(cart, order);
    order.setItems(orderItems);

    Order savedOrder = orderRepository.save(order);
    cartService.clearCart(userId);

    try {
      emailService.sendOrderConfirmation(savedOrder);
    } catch (MailException e) {
      logger.error("Failed to send order confirmation email for order ID: " + savedOrder.getId(), e);
    }

    return orderMapper.toDTO(savedOrder);
  }

  private List<OrderItem> createOrderItems(Cart cart, Order order) {
    return cart.getItems().stream().map(cartItem -> {
      Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(() -> new EntityNotFoundException("Product Not Found with ID: " + cartItem.getProduct().getId()));

      if (product.getQuantity() == null) {
        throw new IllegalStateException("Product quantity is not set for product " + product.getName());
      }

      if (product.getQuantity() < cartItem.getQuantity()) {
        throw new InsufficientStockException("Not enough stock for product " + product.getName());
      }

      product.setQuantity(product.getQuantity() - cartItem.getQuantity());

      productRepository.save(product);
      return new OrderItem(null, order, product, cartItem.getQuantity(), product.getPrice());

    }).collect(Collectors.toList());
  }

  public List<OrderDTO> getAllOrders() {
    return orderMapper.toDTOs(orderRepository.findAll());
  }

  public List<OrderDTO> getUserOrders(Long userId) {
    return orderMapper.toDTOs(orderRepository.findByUserId(userId));
  }
}
