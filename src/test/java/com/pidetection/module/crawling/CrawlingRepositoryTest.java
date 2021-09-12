package com.pidetection.module.crawling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CrawlingRepositoryTest {
    @Autowired
    CrawlingRepository crawlingRepository;


    @DisplayName("findUrlByHash Test")
    @Test
    void findUrlByHash(){
        String hash = "jasdgh2jg";
        String hash2 = "sssdsgh2jg";
        String url = "www.test.com";
        String url2 = "www.test2.com";

        List<Crawling> crawlings = new ArrayList<>();

        crawlings.add(Crawling.builder()
                .url(url)
                .hash(hash)
                .build());
        crawlings.add(Crawling.builder()
                .url(url2)
                .hash(hash2)
                .build());

        crawlingRepository.saveAll(crawlings);

        List<String> urlByHash = crawlingRepository.findUrlByHash(hash);

        assertThat(urlByHash.size()).isEqualTo(1);
        assertThat(urlByHash.get(0)).isEqualTo(url);

    }

}