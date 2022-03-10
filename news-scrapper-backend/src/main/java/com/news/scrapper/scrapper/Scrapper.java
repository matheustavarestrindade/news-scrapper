package com.news.scrapper.scrapper;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.news.scrapper.websites.Website;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.Getter;

public abstract class Scrapper {

    @Getter
    private Website website;

    public Scrapper(Website website) {
        this.website = website;
    }

    public abstract void execute();

    protected JsonElement getAPIContent() {
        try {
            String data = Jsoup.connect(website.getUrl()).ignoreContentType(true).execute().body();
            return JsonParser.parseString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Document getWebsiteContent() {
        try {
            Document doc = Jsoup.connect(getWebsite().getUrl()).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Document getNewsPageDocument(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}