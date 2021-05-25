package com.back.pidetection.web;

import com.back.pidetection.domain.crawling.CrawlingRepository;
import com.back.pidetection.service.DetectionService;
import com.back.pidetection.web.dto.DetectionResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DetectionController {
    private final DetectionService detectionService;
    private final CrawlingRepository crawlingRepository;
    private String inputImage="";


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/input")
    public String inputPage(HttpSession session) {
        session.setAttribute("results",new ArrayList<DetectionResultDto>());
        return "input";
    }

    @PostMapping("/api/result/inputView")
    public @ResponseBody String resultInputImg( @RequestParam("image") MultipartFile image, HttpSession session) throws IOException, InterruptedException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        inputImage = Base64.getEncoder().encodeToString(image.getBytes());
        session.setAttribute("inputImage", inputImage);

        return "";
    }

    @PostMapping("/api/detection/result")
    public @ResponseBody String saveResult(@RequestParam("face") MultipartFile image,
                             @RequestParam("hash") String hash,
                             @RequestParam("precision") String precision, HttpSession session) throws IOException {
        // hash로 url 리스트 받기
        List<String> urls = crawlingRepository.findAllHash(hash);
        if(urls.size() ==0){
            System.out.println("url 검출 실패.");
            return "hash에 해당하는 url이 없습니다.";
        }
        for(String url :urls){
            DetectionResultDto resultDto = detectionService.getResultDto(image, url, precision);
            System.out.println("=========검출!=========");
            System.out.println("URL :"+resultDto.getUrl());
            System.out.println("PRECSTION : "+resultDto.getPrecision());
            System.out.println("======================");

            ArrayList<DetectionResultDto> resultDtos = (ArrayList<DetectionResultDto>) session.getAttribute("results");
            resultDtos.add(resultDto);
            System.out.println(resultDto.getImage().length());
            session.setAttribute("results",resultDtos);

        }
        return "데이터 수신.";
    }
    @GetMapping("/result")
    public String resultTest2(Model model, HttpSession session){
        ArrayList<DetectionResultDto> resultDtos = (ArrayList<DetectionResultDto>) session.getAttribute("results");

        model.addAttribute("inputImage", inputImage);
        model.addAttribute("results", resultDtos);
        return "result";
    }


    // AI Server쪽 코드 테스트.
    @PostMapping("/api/detection/input")
    public @ResponseBody String inputSaveTest( @RequestParam("image") MultipartFile image, HttpSession session) throws IOException, InterruptedException {
        String sessionId = session.getId();

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

//        cmd로 python 스크립트 실행.
        String cmd[] = new String[3];
        cmd[0] ="/bin/sh";
        // 명령어 모두 실해 후 종료 옵션.
        cmd[1] = "-c";
        cmd[2] = "cd ~/Desktop/Capstone/test && python test.py --sessionId "+sessionId;

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd);


        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        process.getInputStream()));
        String line;
        while((line =br.readLine()) !=null)
            System.out.println(line);

        System.out.println("결과 전송 완료.");
        Thread.sleep(5000);
        System.out.println("매칭 완료.");

        return "/result";

    }



}
