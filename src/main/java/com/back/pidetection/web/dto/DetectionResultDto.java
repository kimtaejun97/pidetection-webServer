package com.back.pidetection.web.dto;

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

    @Builder
    public DetectionResultDto(String url, byte[] image, String precision){
        this.image = Base64.getEncoder().encodeToString(image);
        this.url =url;
        this.precision = precision;
    }

}
