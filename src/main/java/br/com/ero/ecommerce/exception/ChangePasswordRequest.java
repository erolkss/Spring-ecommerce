package br.com.ero.ecommerce.exception;

import lombok.Data;

@Data
public class ChangePasswordRequest {
  private String currentPassword;
  private String newPassword;
}
