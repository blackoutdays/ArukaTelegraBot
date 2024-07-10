package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandHandler {

    public SendMessage handleCommand(Message message) {
        String messageText = message.getText();
        long chatId = message.getChatId();

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
                response.setText("Hello, " + message.getFrom().getFirstName() + "!");
                break;
            default:
                response.setText("Unknown command. Use /help to see available commands.");
                break;
        }
        return response;
    }
}