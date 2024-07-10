package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private static final Logger logger = Logger.getLogger(MyTelegramBot.class.getName());

    @Value("${telegram.bot.adminChatId}")
    private String adminChatId; // Your personal chat ID

    public MyTelegramBot(@Value("${telegram.bot.username}") String botUsername, @Value("${telegram.bot.token}") String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        handleUpdate(update);
    }

    public void onWebhookUpdateReceived(Update update) {
        handleUpdate(update);
    }

    private void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String user = update.getMessage().getFrom().getUserName();
            String messageText = update.getMessage().getText();

            // Log the message details
            logger.log(Level.INFO, "Received message from {0}: {1}", new Object[]{user, messageText});

            SendMessage responseMessage;

            if (messageText.startsWith("/")) {
                // Handle command
                responseMessage = handleCommand(update);
            } else {
                // Handle regular message
                responseMessage = new SendMessage();
                responseMessage.setChatId(String.valueOf(chatId));
                responseMessage.setText("Received your message: " + messageText);
            }

            try {
                // Send response to the user
                execute(responseMessage);

                // Forward the message to the admin if it's not from the admin
                if (!String.valueOf(chatId).equals(adminChatId)) {
                    SendMessage adminMessage = new SendMessage();
                    adminMessage.setChatId(adminChatId);
                    adminMessage.setText("Message from " + user + " (chatId: " + chatId + "): " + messageText);
                    execute(adminMessage);
                }
            } catch (TelegramApiException e) {
                logger.log(Level.SEVERE, "Error sending message", e);
            }
        }
    }

    private SendMessage handleCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        String messageText = update.getMessage().getText();

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        switch (messageText.split(" ")[0]) {
            case "/start":
                response.setText("Welcome to the bot! Use /help to see available commands.");
                break;
            case "/help":
                response.setText("Available commands:\n/start - Start interacting with the bot\n/help - Get a list of available commands\n/hello - Receive a greeting");
                break;
            case "/hello":
                // Use the user's full name
                response.setText("Hello, " + firstName + " " + (lastName != null ? lastName : "") + "!");
                break;
            default:
                response.setText("Unknown command. Use /help to see available commands.");
                break;
        }

        return response;
    }
}