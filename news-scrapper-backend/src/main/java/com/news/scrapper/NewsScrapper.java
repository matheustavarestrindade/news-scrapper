package com.news.scrapper;

import com.news.scrapper.database.Database;
import com.news.scrapper.scrapper.BizNewsScrapper;
import com.news.scrapper.scrapper.G1EconomiaScrapper;
import com.news.scrapper.scrapper.R7EconomiaScrapper;
import com.news.scrapper.scrapper.UOLEconomiaScrapper;
import com.news.scrapper.telegram.TelegramBotHandler;

import lombok.Getter;

public final class NewsScrapper {

    @Getter
    private static Database database;
    @Getter
    private static TelegramBotHandler telegramBotHandler;

    public static void main(String[] args) {
        System.out.println("[INFO] Starting WebScrapper Services");
        database = new Database("localhost", "3306", "root", "", "news_scrapper");
        database.init();

        telegramBotHandler = new TelegramBotHandler("");

        UOLEconomiaScrapper uolEconomiaScrapper = new UOLEconomiaScrapper();
        // uolEconomiaScrapper.execute();
        G1EconomiaScrapper g1EconomiaScrapper = new G1EconomiaScrapper();
        // g1EconomiaScrapper.execute();
        R7EconomiaScrapper r7EconomiaScrapper = new R7EconomiaScrapper();
        // r7EconomiaScrapper.execute();
        BizNewsScrapper bizNewsScrapper = new BizNewsScrapper();
        // bizNewsScrapper.execute();

    }
}
