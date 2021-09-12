package com.pidetection.module.crawling;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    @PostMapping("/face")
    public Long save(@RequestBody Map<String, String> imageUrlAndHash){
        return crawlingService.save(imageUrlAndHash);
    }
}
