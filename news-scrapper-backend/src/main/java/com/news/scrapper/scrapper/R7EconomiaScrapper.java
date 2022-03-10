package com.news.scrapper.scrapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.news.scrapper.models.NewsModel;
import com.news.scrapper.websites.Website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class R7EconomiaScrapper extends Scrapper {

    public R7EconomiaScrapper() {
        super(Website.R7_ECONOMIA);
    }

    public void execute() {
        JsonElement res = getAPIContent();
        if (res == null || !(res instanceof JsonArray)) return;
        JsonArray newsJson = res.getAsJsonArray();
        for (int i = 0; i < newsJson.size(); i++) {
            JsonObject newJson = newsJson.get(i).getAsJsonObject();
            String headline = newJson.get("title").getAsString();
            String url = newJson.get("url").getAsString();
            NewsModel newsModel = new NewsModel(getWebsite(), headline, url, "");
            newsModel.saveToDatabase();
        }

    }

}
