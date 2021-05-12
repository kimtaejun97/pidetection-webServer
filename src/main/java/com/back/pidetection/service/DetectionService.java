package com.back.pidetection.service;

import com.back.pidetection.web.dto.DetectionResultDto;
import lombok.RequiredArgsConstructor;
import org.h2.util.json.JSONObject;
import org.h2.util.json.JSONValue;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DetectionService {

    public DetectionResultDto getResultDto(MultipartFile image, String url, String precision) throws IOException {
        DetectionResultDto detectionResultDto = DetectionResultDto.builder()
                .image(image.getBytes())
                .url(url)
                .precision(precision)
                .build();

        return detectionResultDto;


    }
}
