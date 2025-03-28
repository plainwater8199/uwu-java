package com.uwu.study.water.api;


import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class apiTest {

    @PostMapping("/callback")
    public ApiResponse callback(ServletRequest servletRequest, ServletResponse servletResponse) {
        StringBuilder requestBody = new StringBuilder();
        String  result = "";
        try (BufferedReader reader = servletRequest.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            System.out.println(requestBody);
            JsonObject json = new Gson().fromJson(requestBody.toString(), JsonObject.class); // 使用Gson解析为JsonObject
            String status = json.get("state").getAsString(); // 获取指定key的value，并转为String返回
            String desc = json.get("desc").getAsString();
            result = status + "--" + desc;
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse(500, "Error reading request body", null);
        }
        return new ApiResponse(200, "Success", result);
    }


    /**
     * 响应结果封装类
     */
    public static class ApiResponse {
        private int code;
        private String message;
        private String data;

        public ApiResponse(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        // Getter 和 Setter 方法
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }





}
