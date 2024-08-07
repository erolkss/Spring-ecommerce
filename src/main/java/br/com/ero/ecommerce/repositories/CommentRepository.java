package br.com.ero.ecommerce.repositories;

import br.com.ero.ecommerce.model.Cart;
import br.com.ero.ecommerce.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByProductId(Long productId);
}
