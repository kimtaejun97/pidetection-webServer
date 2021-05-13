package com.back.pidetection.web;

import com.back.pidetection.service.CrawlingService;
import com.back.pidetection.web.dto.CrawlingSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CrawlingApiController {

    private final CrawlingService crawlingService;

    @PostMapping("/api/saveface")
    public Long save(@RequestBody Map<String, String> reqeust){
        return crawlingService.save(reqeust);
    }
}
