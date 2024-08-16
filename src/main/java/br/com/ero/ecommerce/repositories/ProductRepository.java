package br.com.ero.ecommerce.repositories;

import br.com.ero.ecommerce.dto.ProductListDTO;
import br.com.ero.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("SELECT new br.com.ero.ecommerce.dto.ProductListDTO(p.id, p.name, p.description, p.price, p.quantity, p.image) FROM Product p")
  List<ProductListDTO> findAllWithoutComments();

}
