package com.q.airobotbackend.controller;

import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author Q.
 * @Date 2025-11-14 17:48
 */
@RestController
@RequestMapping("/ai")
public class DeepSeekChatController {

    @Autowired
    private DeepSeekChatModel chatModel;
    /**
     * 获取深度搜索结果
     *
     * @param message
     * @return
     */
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁? 并回答中加入我爱曦曦") String message) {
        return chatModel.call(message);
    }

}
