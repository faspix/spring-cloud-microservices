package com.faspix.notification.service.deposit;

import com.faspix.notification.config.deposit.RabbitMQConfigurationDeposit;
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
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfigurationDeposit.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        log.debug(String.valueOf(message));
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        log.debug(String.valueOf(depositResponseDto));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDto.getEmail());
        mailMessage.setFrom("lori@cat.xyz");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit, sum: " + depositResponseDto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

}
