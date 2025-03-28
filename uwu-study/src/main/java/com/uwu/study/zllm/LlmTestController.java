package com.uwu.study.zllm;


import com.uwu.study.zllm.vo.MockReq;
import com.uwu.study.zllm.vo.MockResp;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
public class LlmTestController {


    @PostMapping("/llm/mock")
    public MockResp mock(@RequestBody MockReq req) {
        MockResp resp = new MockResp();
        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 定义格式化规则
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        resp.setTime(now.format(formatter));
        resp.setCount(0);
        if(req != null){
            resp.setCount(req.getContent().length());
        }
        return resp;
    }
}
