//package org.example;
//
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//public class DeveloperAssistantBot extends TelegramLongPollingBot {
//    private final String botToken;
//
//    public DeveloperAssistantBot(String botToken) {
//        this.botToken = botToken;
//    }
//
//    @Override
//    public String getBotUsername() {
//        return "Aruka Bot"; // Замените на имя вашего бота
//    }
//
//    @Override
//    public String getBotToken() {
//        return botToken;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            long chatId = update.getMessage().getChatId();
//            String messageText = update.getMessage().getText();
//
//            // Вывод chatId в консоль для получения
//            System.out.println("Chat ID: " + chatId);
//
//            SendMessage message = new SendMessage();
//            message.setChatId(String.valueOf(chatId));
//            message.setText("Ваш chatId: " + chatId);
//
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class DeveloperAssistantBot extends TelegramLongPollingBot {
    private final String botToken;

    public DeveloperAssistantBot(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername"; // Замените на имя вашего бота
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            System.out.println("Received message from chat ID: " + chatId);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Ваш chatId: " + chatId);

            try {
                execute(message);
                System.out.println("Sent message to chat ID: " + chatId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}