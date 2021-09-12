package com.pidetection.module.detection;

import com.pidetection.module.crawling.CrawlingRepository;
import com.pidetection.module.detection.dto.DetectionResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetectionService {

    @Autowired
    CrawlingRepository crawlingRepository;

    public DetectionResultDto makeResultDto(MultipartFile image, String url, String precision) throws IOException {
        return new DetectionResultDto(image.getBytes(), url, precision);
    }
    public List<DetectionResultDto> makeDetectionResultDtos(MultipartFile image, String hash, String precision) throws IOException {
        List<DetectionResultDto> resultDtos = new ArrayList<>();
        List<String> urls = crawlingRepository.findUrlByHash(hash);

        for(String url :urls){
            resultDtos.add(makeResultDto(image, url, precision));
            log.info("검출된 URL: "+ url);
        }
        return resultDtos;
    }
}
