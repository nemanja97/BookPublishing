package lu.ftn.services;

public interface EmailNotificationService {

    void sendMessage(String to, String subject, String content);
}
