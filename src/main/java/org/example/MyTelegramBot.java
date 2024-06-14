package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyTelegramBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;

    // Конструктор класса
    public MyTelegramBot(String botUsername, String botToken) {
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Hi, Aruka <3!");
            message.setText("hi");

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для обработки входящих обновлений через webhook
    public void onWebhookUpdateReceived(Update update) {
        // Проверяем, есть ли текстовое сообщение в обновлении
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Получаем идентификатор чата
            long chatId = update.getMessage().getChatId();

            // Получаем текст сообщения
            String text = update.getMessage().getText();

            // Выводим информацию о сообщении на консоль
            System.out.println("Received message in chat " + chatId + ": " + text);

            // Отправляем ответное сообщение
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Received your message: " + text);

            try {
                execute(message); // Отправляем сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}





////
////package org.example;
////
////import org.telegram.telegrambots.meta.api.objects.Update;
////import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
////import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
////import org.telegram.telegrambots.meta.generics.BotOptions;
////import org.telegram.telegrambots.meta.generics.LongPollingBot;
////import org.telegram.telegrambots.meta.TelegramBotsApi;
////import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
////import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
////
////import java.util.List;
////
////public class MyTelegramBot implements LongPollingBot {
////
////    private static final String BOT_USERNAME = "your_bot_username";
////    private static final String BOT_TOKEN = "your_bot_token";
////
////    @Override
////    public String getBotUsername() {
////        return BOT_USERNAME;
////    }
////
////    @Override
////    public String getBotToken() {
////        return BOT_TOKEN;
////    }
////
////    @Override
////    public void onUpdateReceived(Update update) {
////        if (update.hasMessage() && update.getMessage().hasText()) {
////            long chatId = update.getMessage().getChatId();
////            // Обработка полученного сообщения
////        }
////    }
////
////    @Override
////    public BotOptions getOptions() {
////        return null;
////    }
////
////    @Override
////    public void clearWebhook() throws TelegramApiRequestException {
////
////    }
////
////    public static void main(String[] args) {
////        try {
////            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
////            MyTelegramBot bot = new MyTelegramBot();
////            botsApi.registerBot(bot);
////            bot.start();
////        } catch (TelegramApiException e) {
////            e.printStackTrace();
////        }
////    }
////
////    public void start() {
////        try {
////            // Подключение к Telegram API
////
////            // Запрашиваем обновления
////            while (true) {
////                try {
////                    GetUpdates request = new GetUpdates();
////                    List<Update> updates = executeGetUpdates(request);
////                    for (Update update : updates) {
////                        onUpdateReceived(update);
////                    }
////                } catch (TelegramApiException e) {
////                    if (e.getMessage().contains("409")) {
////                        // Если ошибка 409 (конфликт), повторяем запрос через некоторое время
////                        Thread.sleep(1000); // Пауза в 1 секунду перед повторным запросом
////                        continue;
////                    } else {
////                        e.printStackTrace();
////                        break;
////                    }
////                }
////            }
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////    }
////
////    // Метод для выполнения запроса на получение обновлений
////    private List<Update> executeGetUpdates(GetUpdates request) throws TelegramApiException {
////        // Выполняем запрос на получение обновлений
////        return execute(request);
////    }
////
////    // Метод для выполнения запроса к Telegram API
////    private List<Update> execute(GetUpdates request) throws TelegramApiException {
////        // Ваша реализация метода, который будет выполнять запрос к Telegram API
////        return null; // Верните список обновлений (или null, если обновлений нет)
////    }
//////}
//package org.example;
//
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
//import org.telegram.telegrambots.meta.generics.BotOptions;
//import org.telegram.telegrambots.meta.generics.LongPollingBot;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//import java.util.List;
//
//public class MyTelegramBot implements LongPollingBot {
//
//    private final String botUsername;
//    private final String botToken;
//
//    public MyTelegramBot(String botUsername, String botToken) {
//        this.botUsername = botUsername;
//        this.botToken = botToken;
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
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
//
//            SendMessage message = new SendMessage();
//            message.setChatId(String.valueOf(chatId));
//            message.setText("HI!");
//
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public BotOptions getOptions() {
//        return null;
//    }
//
//    @Override
//    public void clearWebhook() throws TelegramApiRequestException {
//
//    }
//
//    public void start() {
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(this);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<Update> executeGetUpdates(SendMessage request) throws TelegramApiException {
//        return execute(request);
//    }
//
//    private List<Update> execute(SendMessage request) throws TelegramApiException {
//        return null;
//    }
//}