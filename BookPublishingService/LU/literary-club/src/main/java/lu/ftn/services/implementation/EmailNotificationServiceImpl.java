package lu.ftn.services.implementation;

import lu.ftn.services.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String to, String subject, String content) {
        SimpleMailMessage message = formMessage(to, subject, content);
        javaMailSender.send(message);
    }

    private SimpleMailMessage formMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("70bfd8b27b-edeaeb@inbox.mailtrap.io");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }
}
