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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String url;

    @Column(nullable = false)
    private String hash;

    @Builder
    public Crawling(String url, String hash){
        this.url = url;
        this.hash= hash;
    }
}
