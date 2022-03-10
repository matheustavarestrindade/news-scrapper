package com.news.scrapper.websites;

import lombok.Getter;

public enum Website {
    UOL_ECONOMIA("https://economia.uol.com.br/ultimas/");

    @Getter
    private String url;

    Website(String url) {
        this.url = url;
    }

}
