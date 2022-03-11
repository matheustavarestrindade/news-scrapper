package com.news.scrapper.telegram;

import java.util.ArrayList;
import java.util.List;

import com.news.scrapper.models.NewsModel;
import com.news.scrapper.models.TelegramNewsNotificationModel;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Chat.Type;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class TelegramBotHandler {

    private final TelegramBot bot;
    private final BotUpdateLister updateLister;

    public TelegramBotHandler(String telegramToken) {
        this.bot = new TelegramBot(telegramToken);
        this.updateLister = new BotUpdateLister();
        this.bot.setUpdatesListener(this.updateLister);
    }

    public void sendNews(NewsModel news) {
        for (TelegramNewsNotificationModel model : TelegramNewsNotificationModel.getAllActiveFromDatabase()) {
            SendMessage msg = new SendMessage(model.getChatId(), "<a href=\"" + news.getUrl() + "\">" + news.getHeadline() + "</>").parseMode(ParseMode.HTML);
            bot.execute(msg);
        }
    }

    private class BotUpdateLister implements UpdatesListener {

        @Override
        public int process(List<Update> updates) {
            for (Update update : updates) {
                if (update.message() == null) continue;
                if (update.message().text() == null) continue;
                if (update.message().text().startsWith("/notifications")) {
                    String value = update.message().text().replace("/notifications ", "");
                    long chat_id = update.message().chat().id();
                    if (value.equalsIgnoreCase("on")) {
                        TelegramNewsNotificationModel notificationModel = TelegramNewsNotificationModel.getFromDatabase(chat_id);
                        bot.execute(new SendMessage(update.message().chat().id(), "Notificações de notícias ativadas!"));
                        if (notificationModel != null) {
                            notificationModel.setActive(true);
                            notificationModel.saveToDatabase();
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }
                        notificationModel = new TelegramNewsNotificationModel(chat_id, true);
                        notificationModel.saveToDatabase();

                    } else if (value.equalsIgnoreCase("off")) {
                        TelegramNewsNotificationModel notificationModel = TelegramNewsNotificationModel.getFromDatabase(chat_id);
                        bot.execute(new SendMessage(update.message().chat().id(), "Notificações de notícias desativadas!"));
                        if (notificationModel != null) {
                            notificationModel.setActive(false);
                            notificationModel.saveToDatabase();
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }
                        notificationModel = new TelegramNewsNotificationModel(chat_id, false);
                        notificationModel.saveToDatabase();
                    } else {
                        bot.execute(new SendMessage(update.message().chat().id(), "Opção invalida, tente (on/off) como argumento."));
                    }
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }

            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

    }

}
