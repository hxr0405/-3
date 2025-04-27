package com.HeartStream.heartStreamBack.service;

import com.HeartStream.heartStreamBack.utils.TypewriterEffect;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class DeepSeekClient {

    private static String API_KEY;
    private static String API_URL;

    static {
        Properties properties = new Properties();
        try {
            InputStream is = DeepSeekClient.class.getClassLoader().getResourceAsStream("config.properties");
            if (is == null) {
                System.out.println("当前类路径: " + System.getProperty("java.class.path"));
                throw new FileNotFoundException("无法找到属性文件 config.properties");
            }
            properties.load(is);
            API_KEY = properties.getProperty("key");
            API_URL = properties.getProperty("url");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载配置文件时出错", e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** 你好，我是您的心理健康助手。您可以随时倾诉感受，退出请输入 bye。 ****");
        while (true) {
            System.out.println("---请说您问题：---");
            String question = scanner.nextLine().trim();
            if ("bye".equalsIgnoreCase(question)) {
                break;
            }
            ask(question);
            System.out.println();
        }
        System.out.println("再见，希望我的回答帮助到了你，祝你生活愉快！");
        scanner.close();
    }

    public static String ask(String content) {
        List<Message> messages = new ArrayList<>();
        String spt = "你是一位善良，专业且充满同理心的心理咨询师";
        messages.add(new Message("system", spt));
        messages.add(new Message("user", content));

        ChatRequest requestBody = new ChatRequest(
                "deepseek-chat",  // 模型名称，根据文档调整
                messages,
                0.7,  // temperature
                1000  // max_tokens
        );

        System.out.println(">>>正在提交问题...");
        long startTime = System.currentTimeMillis();
        String response = sendRequest(requestBody);
        long endTime = System.currentTimeMillis();
        System.out.println("思考用时：" + (endTime - startTime) / 1000 + "秒");
        TypewriterEffect.printWord(response, 20);
        return response;
    }

    private static String sendRequest(ChatRequest requestBody) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String requestBodyJson = gson.toJson(requestBody);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            System.out.println(">>>已提交问题，正在思考中....");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ChatResponse chatResponse = gson.fromJson(response.body(), ChatResponse.class);
                return chatResponse.getChoices().get(0).getMessage().getContent();
            } else {
                System.err.println("请求失败，状态码: " + response.statusCode() + ", 响应: " + response.body());
                return "请求失败，状态码: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "请求异常: " + e.getMessage();
        }
    }

    static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    static class ChatRequest {
        private String model;
        private List<Message> messages;
        private double temperature;
        private int max_tokens;

        public ChatRequest(String model, List<Message> messages, double temperature, int max_tokens) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
            this.max_tokens = max_tokens;
        }
    }

    static class ChatResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        static class Choice {
            private Message message;

            public Message getMessage() {
                return message;
            }
        }
    }
}

