package com.news.scrapper.models;

import java.util.ArrayList;

import com.news.scrapper.NewsScrapper;

import lombok.Data;

@Data
public class TelegramNewsNotificationModel {

    private long chatId;
    private boolean active;

    public TelegramNewsNotificationModel(long chat_id, boolean active) {
        this.chatId = chat_id;
        this.active = active;
    }

    public static ArrayList<TelegramNewsNotificationModel> getAllActiveFromDatabase() {
        Object modelObj = NewsScrapper.getDatabase().query("SELECT * FROM telegram_news_notification WHERE active=?", (stmt) -> {
            stmt.setBoolean(1, true);
        }, (rs -> {
            ArrayList<TelegramNewsNotificationModel> models = new ArrayList<>();
            while (rs.next()) {
                TelegramNewsNotificationModel model = new TelegramNewsNotificationModel(rs.getLong("chat_id"), rs.getBoolean("active"));
                models.add(model);
            }
            return models;
        }));
        ArrayList<TelegramNewsNotificationModel> models = new ArrayList<TelegramNewsNotificationModel>();
        if (modelObj instanceof ArrayList) {
            ArrayList<?> modelsArray = (ArrayList<?>) modelObj;
            for (Object o : modelsArray) {
                if (o instanceof TelegramNewsNotificationModel) models.add((TelegramNewsNotificationModel) o);
            }
        }
        return models;
    }

    public static TelegramNewsNotificationModel getFromDatabase(long chat_id) {
        Object modelObj = NewsScrapper.getDatabase().query("SELECT * FROM telegram_news_notification WHERE chat_id=?", (stmt) -> {
            stmt.setLong(1, chat_id);
        }, (rs -> {
            TelegramNewsNotificationModel model = null;
            if (rs.next()) {
                model = new TelegramNewsNotificationModel(rs.getLong("chat_id"), rs.getBoolean("active"));
            }
            return model;
        }));
        return modelObj instanceof TelegramNewsNotificationModel ? (TelegramNewsNotificationModel) modelObj : null;
    }

    public void saveToDatabase() {
        NewsScrapper.getDatabase().execute("INSERT IGNORE INTO telegram_news_notification (chat_id, active) VALUES (?,?) ON DUPLICATE KEY UPDATE active=?", (stmt) -> {
            stmt.setLong(1, this.chatId);
            stmt.setBoolean(2, this.active);
            stmt.setBoolean(3, this.active);
        });
    }

}
