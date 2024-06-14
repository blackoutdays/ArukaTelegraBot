//package org.example;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import com.ngrok.Session;
//import java.io.IOException;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@SpringBootApplication
//public class TelegramBotApplication {
//
//    private static final AtomicBoolean isBotRunning = new AtomicBoolean(false);
//    private static Session session;
//
//    public static void main(String[] args) throws IOException {
//        // Проверка, запущен ли уже другой экземпляр бота
//        try (Socket ignored = new Socket("localhost", 8080)) {
//            System.out.println("Another instance of the bot is already running.");
//            return;
//        } catch (IOException e) {
//            // Нет другого экземпляра, продолжаем запуск бота
//        }
//
//        // Запуск приложения Spring Boot
//        SpringApplication.run(TelegramBotApplication.class, args);
//
//        // Запуск бота только если он еще не запущен
//        if (!isBotRunning.compareAndSet(false, true)) {
//            System.out.println("Another instance of the bot is already running.");
//            return;
//        }
//
//        try {
//            // Подключение к сессии Ngrok
//            session = Session.withAuthtokenFromEnv().metadata("my session").connect();
//
//            final Charset utf8 = StandardCharsets.UTF_8;
//
//            try (final var listener = session.httpEndpoint().metadata("my listener").listen()) {
//                System.out.println("ngrok url: " + listener.getUrl());
//                final var buf = ByteBuffer.allocateDirect(1024);
//
//                while (true) {
//                    final var conn = listener.accept();
//                    buf.clear();
//                    conn.read(buf);
//
//                    System.out.println(utf8.decode(buf));
//
//                    buf.clear();
//                    buf.put("HTTP/1.0 200 OK\n\nHello from ngrok!".getBytes(utf8));
//                    buf.flip();
//                    conn.write(buf);
//                    conn.close();
//                }
//            }
//        } finally {
//            // Установка флага обратно в false после завершения работы бота
//            isBotRunning.set(false);
//        }
//    }
//
//    // Метод для остановки бота
//    public static void stop() {
//        if (session != null) {
//            session.httpEndpoint();
//        }
//    }
//}


//
//
//
//package org.example;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.generics.LongPollingBot;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//import com.ngrok.Session;
//import java.io.IOException;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@SpringBootApplication
//public class TelegramBotApplication {
//
//    private static final AtomicBoolean isBotRunning = new AtomicBoolean(false);
//    private static Session session;
//
//    public static void main(String[] args) throws IOException {
//        boolean isRunning = true; // Флаг для управления циклом
//
//        try (Socket ignored = new Socket("localhost", 8080)) {
//            System.out.println("Another instance of the bot is already running.");
//            isRunning = false; // Если другой экземпляр бота уже запущен, выходим из цикла
//        } catch (IOException e) {
//            // Продолжаем запуск бота
//        }
//
//        SpringApplication.run(TelegramBotApplication.class, args);
//
//        if (!isBotRunning.compareAndSet(false, true)) {
//            System.out.println("Another instance of the bot is already running.");
//            isRunning = false; // Если другой экземпляр бота уже запущен, выходим из цикла
//        }
//
//        try {
//            session = Session.withAuthtokenFromEnv().metadata("my session").connect();
//            final Charset utf8 = StandardCharsets.UTF_8;
//
//            try (final var listener = session.httpEndpoint().metadata("my listener").listen()) {
//                System.out.println("ngrok url: " + listener.getUrl());
//                final var buf = ByteBuffer.allocateDirect(1024);
//
//                while (isRunning) { // Изменение условия цикла
//                    final var conn = listener.accept();
//                    buf.clear();
//                    conn.read(buf);
//                    System.out.println(utf8.decode(buf));
//                    buf.clear();
//                    buf.put("HTTP/1.0 200 OK\n\nHello from ngrok!".getBytes(utf8));
//                    buf.flip();
//                    conn.write(buf);
//                    conn.close();
//                }
//            }
//        } finally {
//            isBotRunning.set(false);
//        }
//    }
//
//    public static void stop() {
//        if (session != null) {
//            session.httpEndpoint();
//        }
//    }
//}

package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.ngrok.Session;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
public class TelegramBotApplication {

    private static final AtomicBoolean isBotRunning = new AtomicBoolean(false);
    private static Session session;

    public static void main(String[] args) throws IOException {
        boolean isRunning = true; // Флаг для управления циклом

        try (Socket ignored = new Socket("localhost", 8080)) {
            System.out.println("Another instance of the bot is already running.");
            isRunning = false; // Если другой экземпляр бота уже запущен, выходим из цикла
        } catch (IOException e) {
            // Продолжаем запуск бота
        }

        SpringApplication.run(TelegramBotApplication.class, args);

        if (!isBotRunning.compareAndSet(false, true)) {
            System.out.println("Another instance of the bot is already running.");
            isRunning = false; // Если другой экземпляр бота уже запущен, выходим из цикла
        }

        try {
            session = Session.withAuthtokenFromEnv().metadata("my session").connect();
            final Charset utf8 = StandardCharsets.UTF_8;

            try (final var listener = session.httpEndpoint().metadata("my listener").listen()) {
                System.out.println("ngrok url: " + listener.getUrl());
                final var buf = ByteBuffer.allocateDirect(1024);

                while (isRunning) { // Изменение условия цикла
                    final var conn = listener.accept();
                    buf.clear();
                    conn.read(buf);
                    System.out.println(utf8.decode(buf));
                    buf.clear();
                    buf.put("HTTP/1.0 200 OK\n\nHello from ngrok!".getBytes(utf8));
                    buf.flip();
                    conn.write(buf);
                    conn.close();

                    // Обновляем переменную isRunning внутри цикла
                    // Например, можно добавить условие для выхода из цикла в зависимости от полученных данных
                    if (conditionMet()) {
                        isRunning = false;
                    }
                }
            }
        } finally {
            isBotRunning.set(false);
        }
    }

    public static void stop() {
        if (session != null) {
            session.httpEndpoint();
        }
    }

    private static boolean conditionMet() {
        // Здесь можно добавить проверку на какое-то условие для выхода из цикла
        return false;
    }
}
//hi