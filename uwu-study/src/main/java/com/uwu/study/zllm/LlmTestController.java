package com.uwu.study.zllm;


import com.uwu.study.zllm.vo.MockResp;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
public class LlmTestController {


    @GetMapping("/mock")
    public MockResp mock() {
        MockResp resp = new MockResp();
        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 定义格式化规则
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        resp.setTime(now.format(formatter));
        resp.setCount(0);

        return resp;
    }
}
