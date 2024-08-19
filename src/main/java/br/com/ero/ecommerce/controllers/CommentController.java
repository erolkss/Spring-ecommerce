package br.com.ero.ecommerce.controllers;

import br.com.ero.ecommerce.dto.CommentDTO;
import br.com.ero.ecommerce.model.User;
import br.com.ero.ecommerce.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;


  @PostMapping("/product/{productId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<CommentDTO> addComment(@PathVariable Long productId,
                                               @AuthenticationPrincipal UserDetails userDetails,
                                               @Valid @RequestBody CommentDTO commentDTO) {
    Long userId = ((User) userDetails).getId();

    return ResponseEntity.ok(commentService.addComment(productId, userId, commentDTO));
  }
}
