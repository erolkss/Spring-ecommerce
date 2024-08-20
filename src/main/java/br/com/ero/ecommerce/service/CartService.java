package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.dto.CartDTO;
import br.com.ero.ecommerce.exception.InsufficientStockException;
import br.com.ero.ecommerce.exception.ResourceNotFoundException;
import br.com.ero.ecommerce.mapper.CartMapper;
import br.com.ero.ecommerce.model.Cart;
import br.com.ero.ecommerce.model.CartItem;
import br.com.ero.ecommerce.model.Product;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.repositories.CartRepository;
import br.com.ero.ecommerce.repositories.ProductRepository;
import br.com.ero.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final CartMapper cartMapper;

  public CartDTO addToCart(Long userId, Long productId, Integer quantity) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Product product = productRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    if (product.getQuantity() < quantity) {
      throw new InsufficientStockException("Not enough available");
    }

    Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(null, user, new ArrayList<>()));
    Optional<CartItem> existingCartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();

    if (existingCartItem.isPresent()) {
      CartItem cartItem = existingCartItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
      CartItem cartItem = new CartItem(null, cart, product, quantity);
      cart.getItems().add(cartItem);
    }

    Cart savedCart = cartRepository.save(cart);

    return cartMapper.toDTO(savedCart);
  }

  public CartDTO getCart(Long userId) {
    Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));

    return cartMapper.toDTO(cart);
  }
}
