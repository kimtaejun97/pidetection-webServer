package com.pidetection.module.detection.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Base64;


@Getter
@NoArgsConstructor
public class DetectionResultDto {
    private String image;
    private String url;
    private String precision;

    public DetectionResultDto(byte[] image, String url, String precision){
        this.image = Base64.getEncoder().encodeToString(image);
        this.url =url;
        this.precision = precision;
    }

}
