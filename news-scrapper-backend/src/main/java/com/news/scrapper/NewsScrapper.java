package com.news.scrapper;

import com.news.scrapper.database.Database;
import com.news.scrapper.scrapper.UOLEconomiaScrapper;

import lombok.Getter;

public final class NewsScrapper {

    @Getter
    private static Database database;

    public static void main(String[] args) {
        System.out.println("[INFO] Starting WebScrapper Services");
        database = new Database("localhost", "3306", "root", "", "news_scrapper");
        database.init();
        UOLEconomiaScrapper uolEconomiaScrapper = new UOLEconomiaScrapper();
        uolEconomiaScrapper.execute();
    }
}
