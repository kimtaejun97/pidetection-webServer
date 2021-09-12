package com.pidetection.module.crawling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrawlingServiceTest {
    @Autowired
    CrawlingService crawlingService;

    @Autowired
    CrawlingRepository crawlingRepository;


    @DisplayName("저장 테스트")
    @Test
    void save(){
        String url = "www.test.com";
        String hash = "adfh123h41";
        Map<String, String> urlAndHash = new HashMap<>(){
            {
                put("url", url);
                put("hash", hash);
            }
        };
        crawlingService.save(urlAndHash);

        Crawling testValue = crawlingRepository.findAll().get(0);
        assertThat(testValue.getUrl()).isEqualTo(url);
        assertThat(testValue.getHash()).isEqualTo(hash);

    }

}