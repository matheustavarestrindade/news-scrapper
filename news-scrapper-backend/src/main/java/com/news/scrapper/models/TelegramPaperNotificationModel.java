package com.news.scrapper.models;

import com.news.scrapper.NewsScrapper;

import lombok.Data;

@Data
public class TelegramPaperNotificationModel {

    private final long chatId;
    private final String paper;
    private final String condition;
    private final double value;
    private final boolean sent;

    public void saveToDatabase() {
        NewsScrapper.getDatabase().execute("INSERT IGNORE INTO telegram_paper_notifications (chat_id,paper,cond,value,sent) VALUES (?,?,?,?,?)", (stmt) -> {
            stmt.setLong(1, this.chatId);
            stmt.setString(2, this.paper);
            stmt.setString(3, this.condition);
            stmt.setDouble(4, this.value);
            stmt.setBoolean(5, this.sent);
        });
    }

}
