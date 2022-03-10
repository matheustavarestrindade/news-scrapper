package com.news.scrapper.scrapper;

import com.news.scrapper.models.NewsModel;
import com.news.scrapper.websites.Website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UOLEconomiaScrapper extends Scrapper {

    public UOLEconomiaScrapper() {
        super(Website.UOL_ECONOMIA);
    }

    @Override
    public void execute() {
        Document doc = getWebsiteContent();
        Elements elements = doc.getElementsByClass("results-items");
        if (elements.size() == 0) return;

        Elements newsElements = doc.getElementsByClass("thumbnails-wrapper");
        for (int index = 0; index < newsElements.size(); index++) {
            Element newsElement = newsElements.get(index);
            Elements newsLinkElement = newsElement.getElementsByTag("a");
            if (newsLinkElement.size() == 0) continue;
            String link = newsLinkElement.get(0).attr("href");
            Elements headerElement = newsElement.getElementsByTag("h3");
            if (headerElement.size() == 0) continue;
            String header = headerElement.text();
            NewsModel scrappedNews = new NewsModel(getWebsite(), header, link, "");
            scrappedNews.saveToDatabase();
        }

    }

}
