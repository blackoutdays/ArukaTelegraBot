//package org.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@RestController
//public class WebhookController {
//
//    private final MyTelegramBot myTelegramBot;
//
//    @Autowired
//    public WebhookController(MyTelegramBot myTelegramBot) {
//        this.myTelegramBot = myTelegramBot;
//    }
//
//    @PostMapping("/webhook")
//    public void onUpdateReceived(@RequestBody Update update) {
//        myTelegramBot.onWebhookUpdateReceived(update);
//    }
//}

package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {

    private final MyTelegramBot myTelegramBot;

    @Autowired
    public WebhookController(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    @PostMapping("/webhook")
    public void onUpdateReceived(@RequestBody Update update) {
        myTelegramBot.onWebhookUpdateReceived(update);
    }
}