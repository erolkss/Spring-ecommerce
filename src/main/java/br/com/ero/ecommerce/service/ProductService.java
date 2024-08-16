package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.dto.ProductDTO;
import br.com.ero.ecommerce.exception.ResourceNotFoundException;
import br.com.ero.ecommerce.mapper.ProductMapper;
import br.com.ero.ecommerce.model.Product;
import br.com.ero.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  private static final String UPLOAD_DIR = "src/main/resources/static/images/";

  @Transactional
  public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws IOException {
    Product product = productMapper.toEntity(productDTO);

    if (image != null && !image.isEmpty()) {
      String fileName = saveImage(image);
      product.setImage("/images/" + fileName);
    }
    Product savedProduct = productRepository.save(product);

    return productMapper.toDTO(savedProduct);
  }

  @Transactional
  public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile image) throws IOException {
    Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    existingProduct.setName(productDTO.getName());
    existingProduct.setDescription(productDTO.getDescription());
    existingProduct.setPrice(productDTO.getPrice());
    existingProduct.setQuantity(productDTO.getQuantity());
    if (image != null && !image.isEmpty()) {
      String fileName = saveImage(image);
      existingProduct.setImage("/images/" + fileName);
    }
    Product updateProduct = productRepository.save(existingProduct);

    return productMapper.toDTO(updateProduct);
  }

  @Transactional
  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException("Product Not Found");
    }
    productRepository.deleteById(id);
  }


  public ProductDTO getProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    return productMapper.toDTO(product);
  }


  private String saveImage(MultipartFile image) throws IOException {
    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
    Path path = Paths.get(UPLOAD_DIR + fileName);
    Files.createDirectories(path.getParent());
    Files.write(path, image.getBytes());

    return fileName;
  }


}
