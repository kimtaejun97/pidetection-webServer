package com.pidetection.module.crawling;

import com.pidetection.module.crawling.dto.CrawlingSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final CrawlingRepository crawlingRepository;

    @Transactional
    public Long save(Map<String, String> requestWithUrlAndHash){
        CrawlingSaveRequestDto requestDto = new CrawlingSaveRequestDto(requestWithUrlAndHash);
        return crawlingRepository.save(requestDto.toEntity()).getId();
    }
}
