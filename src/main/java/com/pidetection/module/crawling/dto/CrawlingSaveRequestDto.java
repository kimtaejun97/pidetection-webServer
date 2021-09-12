package com.pidetection.module.crawling.dto;


import com.pidetection.module.crawling.Crawling;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Getter

@NoArgsConstructor
public class CrawlingSaveRequestDto {
    private String url;
    private String hash;

    public CrawlingSaveRequestDto(Map<String, String> requestWithUrlAndHash){
        this.url = requestWithUrlAndHash.get("url");
        this.hash =requestWithUrlAndHash.get("hash");
    }

    public Crawling toEntity(){
        return Crawling.builder()
                .url(url)
                .hash(hash)
                .build();
    }


}

