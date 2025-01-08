package com.uwu.study.disruptor;

import com.uwu.study.disruptor.producer.MyEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyEventProducer myEventProducer;

    @Autowired
    public MyController(MyEventProducer myEventProducer) {
        this.myEventProducer = myEventProducer;
    }

    @PostMapping("/publish")
    public String publishEvent(@RequestParam("value") long value) {
        myEventProducer.onData(value);
        return "Event published";
    }
}
