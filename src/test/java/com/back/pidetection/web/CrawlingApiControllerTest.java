package com.back.pidetection.web;

import com.back.pidetection.domain.crawling.Crawling;
import com.back.pidetection.domain.crawling.CrawlingRepository;
import com.back.pidetection.web.dto.CrawlingSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrawlingApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CrawlingRepository crawlingRepository;

    @After
    public void cleanup(){
        crawlingRepository.deleteAll();
    }

    @Test
    public void save(){
        String url = "http://www.testurl";
        String hash = "asdfdfd0f0";
        Map<String, String> data = new HashMap<>();
        data.put("url", url);
        data.put("hash", hash);

//        CrawlingSaveRequestDto requestDto = CrawlingSaveRequestDto.builder()
//                .fileName(fileName)
//                .url(url)
//                .build();

        String postUrl = "http://localhost:"+port+"api/saveface";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(postUrl,
                data,
                Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Crawling> crawlingList = crawlingRepository.findAll();
        assertThat(crawlingList.get(0).getUrl()).isEqualTo(url);
        assertThat(crawlingList.get(0).getHash()).isEqualTo(hash);

    }

}
