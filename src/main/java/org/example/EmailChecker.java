package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class EmailChecker {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.storeType}")
    private String storeType;

    @Value("${mail.user}")
    private String user;

    @Value("${mail.oauth2.token}")
    private String oauth2Token;

    @Value("${telegram.bot.adminChatId}")
    private String adminChatId;

    private final MyTelegramBot myTelegramBot;

    public EmailChecker(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    @PostConstruct
    public void init() {
        startCheckingEmails();
    }

    public void checkMails() {
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.auth.mechanisms", "XOAUTH2");

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imap");
            store.connect(host, user, oauth2Token);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            for (Message message : messages) {
                String subject = message.getSubject();
                String from = ((MimeMessage) message).getFrom()[0].toString();

                SendMessage telegramMessage = new SendMessage();
                telegramMessage.setChatId(adminChatId);
                telegramMessage.setText("Email from: " + from + ", Subject: " + subject);
                myTelegramBot.execute(telegramMessage);
            }

            emailFolder.close(false);
            store.close();
        } catch (AuthenticationFailedException e) {
            System.err.println("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCheckingEmails() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Checking emails...");
                checkMails();
            }
        }, 0, 60000); // Check every 60 seconds
    }
}