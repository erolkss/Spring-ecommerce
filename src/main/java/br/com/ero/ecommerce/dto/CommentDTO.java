package br.com.ero.ecommerce.dto;

import lombok.Data;

@Data
public class CommentDTO {

  private Long id;
  private String content;
  private Integer score;
  private Long userId;

}
