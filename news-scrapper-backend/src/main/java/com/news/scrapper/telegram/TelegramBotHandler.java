package com.news.scrapper.telegram;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

public class TelegramBotHandler {

    private final TelegramBot bot;
    private final BotUpdateLister updateLister;

    public TelegramBotHandler(String telegramToken) {
        this.bot = new TelegramBot(telegramToken);
        this.updateLister = new BotUpdateLister();
        this.bot.setUpdatesListener(this.updateLister);
    }

    private class BotUpdateLister implements UpdatesListener {

        @Override
        public int process(List<Update> updates) {
            System.out.println(updates.size());
            for (Update update : updates) {
                if (update.message() == null) continue;
                if (update.message().text() == null) continue;
                System.out.println(update.message().text());
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

    }

}
