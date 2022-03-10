package com.news.scrapper.scrapper;

import com.news.scrapper.models.NewsModel;
import com.news.scrapper.websites.Website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BizNewsScrapper extends Scrapper {

    public BizNewsScrapper() {
        super(Website.BIZ_NEWS);
    }

    public void execute() {
        Document doc = getWebsiteContent();
        Elements sectionElement = doc.getElementsByClass("td-main-content-wrap");
        if (sectionElement.size() == 0) return;
        Elements newsBlock = sectionElement.get(0).getElementsByClass("td_block_inner");
        if (newsBlock.size() == 0) return;
        Elements newsSections = newsBlock.get(0).getElementsByClass("entry-title");
        if (newsSections.size() == 0) return;

        for (int i = 0; i < newsSections.size(); i++) {
            Elements headerElement = newsSections.get(i).getElementsByTag("a");
            if (headerElement.size() == 0) continue;
            Element aTag = headerElement.get(0);
            String headline = aTag.text();
            String url = aTag.attr("href");
            NewsModel newsModel = new NewsModel(getWebsite(), headline, url, "");
            newsModel.saveToDatabase();
        }

    }

}
