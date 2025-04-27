package com.HeartStream.heartStreamBack.controller;

import com.HeartStream.heartStreamBack.service.DeepSeekClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/{userId}/aiChat")
public class AIChatController {
    @GetMapping("/chat")
    public String ask(@RequestParam("question") String question, @PathVariable Long userId) {
        return DeepSeekClient.ask(question);
    }
}
