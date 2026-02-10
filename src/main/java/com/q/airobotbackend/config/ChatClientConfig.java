package com.q.airobotbackend.config;

import com.q.airobotbackend.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {


    @Autowired
    private ChatMemory chatMemory;


    /**
     * 初始化 ChatClient 客户端
     * @param chatModel
     * @return
     */
    @Bean
    public ChatClient chatClient(DeepSeekChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("请你扮演一个专业的 Java 架构师，并且用中文回答问题。")
                .defaultAdvisors(new SimpleLoggerAdvisor(), // 添加 Spring AI 内置的日志记录功能
                                 new MyLoggerAdvisor(),   // 添加自定义的日志打印 Advisor
                                 MessageChatMemoryAdvisor.builder(chatMemory).build())

                .build();
    }
}

