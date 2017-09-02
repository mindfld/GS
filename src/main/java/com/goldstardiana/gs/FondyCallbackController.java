package com.goldstardiana.gs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class FondyCallbackController {
    @RequestMapping("/zaza")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
