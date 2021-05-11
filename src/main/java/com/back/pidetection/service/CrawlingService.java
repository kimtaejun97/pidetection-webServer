package com.back.pidetection.service;

import com.back.pidetection.domain.crawling.CrawlingRepository;
import com.back.pidetection.web.dto.CrawlingSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final CrawlingRepository crawlingRepository;

    @Transactional
    public String save(Map<String, String> request){
        CrawlingSaveRequestDto requestDto = CrawlingSaveRequestDto.builder()
                .fileName(request.get("fileName"))
                .url(request.get("url"))
                .build();

        return crawlingRepository.save(requestDto.toEntity()).getFileName();
    }

}
