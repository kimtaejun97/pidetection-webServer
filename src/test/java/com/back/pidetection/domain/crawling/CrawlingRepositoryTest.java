package com.back.pidetection.domain.crawling;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlingRepositoryTest {

    @Autowired
    CrawlingRepository crawlingRepository;

    @After
    public void cleanup(){
        crawlingRepository.deleteAll();
    }

    @Test
    public void saveLoad() throws IOException {
        String url = "https://www.testurl/abc";
        String fileName = "testFileName";

        crawlingRepository.save(Crawling.builder()
                .fileName(fileName)
                .url(url)
                .build()
        );

        List<Crawling> crawlingList = crawlingRepository.findAll();
        assertThat(crawlingList.get(0).getFileName()).isEqualTo(fileName);
        assertThat(crawlingList.get(0).getUrl()).isEqualTo(url);

    }


}
