package br.com.ero.ecommerce.mapper;

import br.com.ero.ecommerce.dto.CommentDTO;
import br.com.ero.ecommerce.dto.ProductDTO;
import br.com.ero.ecommerce.model.Comment;
import br.com.ero.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "image", source = "image")
  ProductDTO toDTO(Product product);

  @Mapping(target = "image", source = "image")
  Product toEntity(ProductDTO productDTO);

  @Mapping(target = "userId", source = "user.id")
  CommentDTO toDTO(Comment comment);

  @Mapping(target = "user.id", source = "userId")
  @Mapping(target = "product", ignore = true)
  Comment toEntity(CommentDTO commentDTO);
}
