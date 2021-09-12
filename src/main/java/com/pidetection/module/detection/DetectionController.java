package com.pidetection.module.detection;

import com.pidetection.module.detection.dto.DetectionResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DetectionController {
    private final DetectionService detectionService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/sessionId")
    public @ResponseBody String getSessionId(HttpSession session){
        return session.getId();
    }

    @GetMapping("/input")
    public String inputPage(HttpSession session) {
        initSession(session);
        return "input";
    }

    @PostMapping("/userInputImage")
    public ResponseEntity setUserInputImageForResultPage(@RequestParam("image") MultipartFile InputImage
            , HttpSession session) throws IOException {
        String userInputImage = encodeUserInputImageToString(InputImage);
        session.setAttribute("inputImage", userInputImage);

        return ResponseEntity.ok().build();
    }

    private String encodeUserInputImageToString(MultipartFile image) throws IOException {
        return Base64.getEncoder().encodeToString(image.getBytes());
    }

    @PostMapping("/result")
    public ResponseEntity saveResult(@RequestParam("face") MultipartFile detectedImage,
                             @RequestParam("hash") String imageHash,
                             @RequestParam("precision") String precision, HttpSession session) throws IOException {

        List<DetectionResultDto> detectedResultDtoOfOneImage = detectionService.makeDetectionResultDtos(detectedImage,imageHash, precision);
        addCurrentDetectedResultToSession(session, detectedResultDtoOfOneImage);

        return ResponseEntity.ok().build();
    }

    private void addCurrentDetectedResultToSession(HttpSession session, List<DetectionResultDto> detectedResultDtoOfOneImage) {
        ArrayList<DetectionResultDto> resultDtos = (ArrayList<DetectionResultDto>) session.getAttribute("results");
        resultDtos.addAll(detectedResultDtoOfOneImage);
        session.setAttribute("results",resultDtos);
    }

    @GetMapping("/result")
    public String resultTest2(Model model, HttpSession session){
        ArrayList<DetectionResultDto> resultDtos = (ArrayList<DetectionResultDto>) session.getAttribute("results");
        model.addAttribute("userInputImage", session.getAttribute("userInputImage"));
        model.addAttribute("results", resultDtos);

        initSession(session);

        return "result";
    }

    private void initSession(HttpSession session) {
        session.setAttribute("results", new ArrayList<DetectionResultDto>());
    }
}

