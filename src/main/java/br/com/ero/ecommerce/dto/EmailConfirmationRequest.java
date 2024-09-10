package br.com.ero.ecommerce.dto;

import lombok.Data;

@Data
public class EmailConfirmationRequest {
  private String email;
  private String confirmationCode;
}
