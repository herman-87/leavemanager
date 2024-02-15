package com.app.leavemanager.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoResources {

    @GetMapping("/hello")
    public String gethello() {
        return "hello";
    }
}
