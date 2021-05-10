package com.back.pidetection.web.dto;


import com.back.pidetection.domain.crawling.Crawling;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CrawlingSaveRequestDto {
    private String fimeName;
    private String url;

    @Builder
    public CrawlingSaveRequestDto(String fileName, String url){
        this.fimeName =fileName;
        this.url = url;
    }

    public Crawling toEntity(){
        return Crawling.builder()
                .fileName(fimeName)
                .url(url)
                .build();
    }


}
