package com.back.pidetection.web;

import com.back.pidetection.service.DetectionService;
import com.back.pidetection.web.dto.DetectionResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class DetectionController {
    private final ArrayList<DetectionResultDto> resultDtos;
    private final DetectionService detectionService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/wait-page")
    public String waitPage(){
        return "wait-page";
    }

    @GetMapping("/input")
    public String inputPage() { return "input"; }

    @PostMapping("/api/detection/result")
    public String saveResult(@RequestParam("face") MultipartFile image,
                             @RequestParam("url") String url,
                             @RequestParam("precision") String precision) throws IOException {

        DetectionResultDto resultDto = detectionService.getResultDto(image, url, precision);

        System.out.println("URL :"+resultDto.getUrl());
        System.out.println("PRECSTION : "+resultDto.getPrecision());

        if (! resultDto.getUrl().equals("")) {
            resultDtos.add(resultDto);
            System.out.println("==검출!==");
            return "/wait-page";
        } else {
            System.out.println("매칭 종료.");

            return "redirect:/result";
        }

    }
    @GetMapping("/result")
    public String resultTest2(Model model){
        model.addAttribute("result",resultDtos);
        return "result";
    }


    // AI Server쪽 코드
    @PostMapping("/api/detection/input")
    public @ResponseBody String inputSaveTest( @RequestParam("image") MultipartFile image) throws IOException, InterruptedException {
        String filePath="/Users/kimtaejun/Desktop/pidetection/src/main/java/com/back/pidetection/web/";

        String fileName = image.getOriginalFilename();
        System.out.println("파일 이름 : "+fileName);
        byte[] im = image.getBytes();
        System.out.println("파일 크기 : "+im.length);

        try{
            image.transferTo(new File(filePath + fileName));

        } catch (IOException e) {
            System.out.println("사용자 이미지 저장 실패.");
            e.printStackTrace();
        }
        System.out.println("사용자 이미지 저장 성공.");

        System.out.println("얼굴 매칭....");
        System.out.println("결과 전송....");
        Thread.sleep(5000);
        System.out.println("종료.");

        return "/result";

    }

}
