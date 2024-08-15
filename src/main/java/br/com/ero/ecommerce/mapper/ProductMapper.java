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

  CommentDTO toDTO(Comment comment);

  Comment toEntity(CommentDTO commentDTO);
}
