package com.news.scrapper.websites;

import lombok.Getter;

public enum Website {
    UOL_ECONOMIA("https://economia.uol.com.br/ultimas/", false), G1_ECONOMIA("https://g1.globo.com/economia/", false),
    R7_ECONOMIA("https://cms-media-api.r7.com/?section[]=518296ef2bc24312eb011e46&page=1&per_page=12", true),
    BIZ_NEWS("https://www.biznews.com.br/category/economia/", false);

    @Getter
    private String url;
    @Getter
    private boolean api;

    Website(String url, boolean api) {
        this.url = url;
        this.api = api;
    }

}
