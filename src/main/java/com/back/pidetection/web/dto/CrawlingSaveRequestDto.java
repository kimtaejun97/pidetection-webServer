package com.back.pidetection.web.dto;


import com.back.pidetection.domain.crawling.Crawling;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CrawlingSaveRequestDto {
    private String url;
    private String hash;

    @Builder
    public CrawlingSaveRequestDto( String url, String hash){
        this.url = url;
        this.hash =hash;
    }

    public Crawling toEntity(){
        return Crawling.builder()
                .url(url)
                .hash(hash)
                .build();
    }


}

