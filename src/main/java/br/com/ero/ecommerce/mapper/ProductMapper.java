package br.com.ero.ecommerce.mapper;

import br.com.ero.ecommerce.dto.ProductDTO;
import br.com.ero.ecommerce.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductDTO toDTO(Product product);

  Product toEntity(ProductDTO productDTO);


}
