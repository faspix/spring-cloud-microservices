package com.faspix.notification.service.payment;

import com.faspix.notification.config.deposit.RabbitMQConfigurationDeposit;
import com.faspix.notification.config.payment.RabbitMQConfigurationPayment;
import com.faspix.notification.service.deposit.DepositResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageHandler {


    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfigurationPayment.QUEUE_PAYMENT)
    public void receive(Message message) throws JsonProcessingException {
        log.debug(String.valueOf(message));
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentResponseDto paymentResponseDto = objectMapper.readValue(jsonBody, PaymentResponseDto.class);
        log.debug(String.valueOf(paymentResponseDto));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(paymentResponseDto.getEmail());
        mailMessage.setFrom("lori@cat.xyz");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit, sum: " + paymentResponseDto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

}
