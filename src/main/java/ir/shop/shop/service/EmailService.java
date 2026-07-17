package ir.shop.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    public void sendVerificationEmail(String to, String code){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("yourgmail@gmail.com");
        message.setTo(to);
        message.setSubject("Verify your account");

        message.setText(
                "Your verification code is: " + code
        );


        mailSender.send(message);
    }

}
