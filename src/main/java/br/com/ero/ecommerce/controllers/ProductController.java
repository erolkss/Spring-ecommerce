package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.ProductDTO;
import br.com.ero.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ProductDTO> createProduct(
          @RequestPart("product") @Valid ProductDTO productDTO,
          @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
    return ResponseEntity.ok(productService.createProduct(productDTO, image));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ProductDTO> updateProduct(
          @PathVariable Long id,
          @RequestPart("product") @Valid ProductDTO productDTO,
          @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

    return ResponseEntity.ok(productService.updateProduct(id, productDTO, image));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

}
