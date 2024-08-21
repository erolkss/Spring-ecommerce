package br.com.ero.ecommerce.service;

import br.com.ero.ecommerce.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;

  @Value("spring.mail.username")
  private String fromEmail;

  public void sendOrderConfirmation(Order order) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(fromEmail);
    message.setTo(order.getUser().getEmail());
    message.setSubject("Order Confirmation");
    message.setText("Your order has been confirmed. Order Id: " + order.getId());

    mailSender.send(message);
  }
}
