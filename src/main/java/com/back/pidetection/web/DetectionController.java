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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DetectionController {
    private final DetectionService detectionService;
    private final CrawlingRepository crawlingRepository;
    private final HashMap<String, ArrayList<DetectionResultDto>> resultMap;
    private final HashMap<String,String> inputImageMap;
    private String inputImage="";


    @GetMapping("/")
    public String index(HttpSession session){
        return "index";
    }

    @GetMapping("/input")
    public String inputPage(HttpSession session) {
        return "input";
    }

    @PostMapping("/api/result/inputView")
    public @ResponseBody String resultInputImg( @RequestParam("image") MultipartFile image) throws IOException, InterruptedException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getRemoteAddr();
        System.out.println(ip);
        inputImage = Base64.getEncoder().encodeToString(image.getBytes());

        inputImageMap.put(ip,inputImage);
        resultMap.put(ip, new ArrayList<DetectionResultDto>());

        return "";
    }

    @PostMapping("/api/detection/result")
    public @ResponseBody String saveResult(@RequestParam("face") MultipartFile image,
                             @RequestParam("hash") String hash,
                             @RequestParam("precision") String precision,
                                           @RequestParam("ip") String ip) throws IOException {

        // hash로 url 리스트 받기
        List<String> urls = crawlingRepository.findAllHash(hash);
        if(urls.size() ==0){
            System.out.println("url 검출 실패.");
            return "hash에 해당하는 url이 없습니다.";
        }

        for(String url :urls){
            DetectionResultDto resultDto = detectionService.getResultDto(image, url, precision);
            System.out.println("=====검출!=====");
            System.out.println("URL :"+resultDto.getUrl());
            System.out.println("PRECSTION : "+resultDto.getPrecision());
            System.out.println("ClientIp : "+ ip);
            System.out.println("MAp :" +resultMap.size());
            ArrayList<DetectionResultDto> resultDtos= resultMap.get(ip);
            resultDtos.add(resultDto);
            resultMap.put(ip,resultDtos);
        }
        return "데이터 수신.";
    }
    @GetMapping("/result")
    public String resultTest2(Model model){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getRemoteAddr();
        ArrayList<DetectionResultDto> resultDtos = resultMap.get(ip);
        String inputImage = inputImageMap.get(ip);
        resultMap.remove(ip);
        inputImageMap.remove(ip);

        model.addAttribute("inputImage", inputImage);
        model.addAttribute("result", resultDtos);
        return "result";
    }


    // AI Server쪽 코드
    @PostMapping("/api/detection/input")
    public @ResponseBody String inputSaveTest( @RequestParam("image") MultipartFile image) throws IOException, InterruptedException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getRemoteAddr();

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
