package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.dto.ProductDTO;
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


  private String saveImage(MultipartFile image) throws IOException {
    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
    Path path = Paths.get(UPLOAD_DIR + fileName);
    Files.createDirectories(path.getParent());
    Files.write(path, image.getBytes());

    return fileName;
  }
}
