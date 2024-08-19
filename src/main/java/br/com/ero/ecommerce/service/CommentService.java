package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.dto.CommentDTO;
import br.com.ero.ecommerce.exception.ResourceNotFoundException;
import br.com.ero.ecommerce.mapper.CommentMapper;
import br.com.ero.ecommerce.model.Comment;
import br.com.ero.ecommerce.model.Product;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.repositories.CommentRepository;
import br.com.ero.ecommerce.repositories.ProductRepository;
import br.com.ero.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final CommentMapper commentMapper;

  public CommentDTO addComment(Long productId, Long userId, CommentDTO commentDTO) {
    Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Comment comment = commentMapper.toEntity(commentDTO);

    comment.setProduct(product);
    comment.setUser(user);
    Comment savedComment = commentRepository.save(comment);

    return commentMapper.toDTO(comment);

  }
}
