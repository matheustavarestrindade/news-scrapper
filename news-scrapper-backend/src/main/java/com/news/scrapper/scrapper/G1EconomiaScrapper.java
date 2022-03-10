package com.news.scrapper.scrapper;

import com.news.scrapper.models.NewsModel;
import com.news.scrapper.websites.Website;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class G1EconomiaScrapper extends Scrapper {

    public G1EconomiaScrapper() {
        super(Website.G1_ECONOMIA);
    }

    @Override
    public void execute() {
        Document doc = getWebsiteContent();
        Elements posts = doc.getElementsByClass("feed-post");
        if (posts.size() == 0) return;

        for (int postIndex = 0; postIndex < posts.size(); postIndex++) {
            Element postElement = posts.get(postIndex);
            Elements aTags = postElement.getElementsByTag("a");
            if (aTags.size() == 0) continue;
            Element aTag = aTags.get(0);
            String headline = aTag.text();
            String url = aTag.attr("href");
            if (!url.startsWith("https://g1.globo")) continue;
            NewsModel news = new NewsModel(getWebsite(), headline, url, "");
            news.saveToDatabase();
        }

    }

}
