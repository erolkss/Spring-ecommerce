package br.com.ero.ecommerce.mapper;

import br.com.ero.ecommerce.dto.CartDTO;
import br.com.ero.ecommerce.dto.CartItemDTO;
import br.com.ero.ecommerce.model.Cart;
import br.com.ero.ecommerce.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CartMapper {
  @Mapping(target = "userId", source = "user.id")
  CartDTO toDTO(Cart cart);

  @Mapping(target = "user.id", source = "userId")
  Cart toEntity(CartDTO cartDTO);

  @Mapping(target = "productId", source = "product.id")
  CartItemDTO toDTO(CartItem cartItem);

  @Mapping(target = "product.id", source = "productId")
  CartItem toEntity(CartItemDTO cartItemDTO);
}
