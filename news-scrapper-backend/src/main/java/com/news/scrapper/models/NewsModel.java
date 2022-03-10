package com.news.scrapper.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.news.scrapper.NewsScrapper;
import com.news.scrapper.websites.Website;

import lombok.Data;

@Data
public class NewsModel {

    private final Website website;

    private final String headline;
    private final String url;
    private final String content;

    private final LocalDateTime scrappedTime = LocalDateTime.now();

    public void saveToDatabase() {
        NewsScrapper.getDatabase().execute("INSERT IGNORE INTO news (headline,url,content,timestamp,publisher) VALUES (?,?,?,?,?)", (stmt) -> {
            stmt.setString(1, this.headline);
            stmt.setString(2, this.url);
            stmt.setString(3, this.content);
            stmt.setTimestamp(4, Timestamp.valueOf(this.scrappedTime));
            stmt.setString(5, this.website.toString());
        });
    }

    public String getStringVersion() {
        String str = "";
        str += "{\n";
        str += "\"website\": \"" + website.toString() + "\",\n";
        str += "\"headline\": \"" + headline + "\",\n";
        str += "\"url\": \"" + url + "\",\n";
        str += "\"content\": \"" + content + "\",\n";
        str += "\"scrappedTime\": \"" + scrappedTime.format(DateTimeFormatter.ISO_DATE_TIME) + "\"\n";
        str += "}";
        return str;
    }

}
