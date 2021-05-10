package com.back.pidetection.domain.crawling;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Crawling {

    @Id
    private String fileName;

    @Column(length = 1000, nullable = false)
    private String url;

    @Builder
    public Crawling(String fileName, String url){
        this.fileName =fileName;
        this.url = url;
    }
}
