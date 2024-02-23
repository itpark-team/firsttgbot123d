package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername = "firsttgbot123d_bot";
    private String botToken = "6578274797:AAFV8mKDkbMO2ktlwDkwKhbyccebcSX7h4A";

    private Random random = new Random();

    private int compNumber, userNumber;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() == true) {
            Message messageFromUser = update.getMessage();

            if (messageFromUser.hasText() == true) {
                String textFromUser = messageFromUser.getText();
                long chatId = messageFromUser.getChatId();

                String textToUser = "";

                if (textFromUser.equals("/start")) {
                    compNumber = random.nextInt(1, 100 + 1);
                    textToUser = "Я загадал число от 1 до 100. Попробуй отгадай. Введи свой вариант:";
                } else {

                    if (NumberUtil.isNumber(textFromUser)) {
                        userNumber = Integer.parseInt(textFromUser);

                        if (userNumber < compNumber) {
                            textToUser = "Введи больше";
                        } else if (userNumber > compNumber) {
                            textToUser = "Введи меньше";
                        } else {
                            textToUser = "Ураа ты угадал. Перезапустите игру командой /start";
                        }
                    } else {
                        textToUser = "Ошибка. Ты ввёл не число";
                    }

                }


                SendMessage messageToUser = new SendMessage();
                messageToUser.setChatId(chatId);
                messageToUser.setText(textToUser);

                try {
                    execute(messageToUser);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
