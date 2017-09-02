package com.goldstardiana.gs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FondyCallbackController {
    @RequestMapping("/zaza")
    public String index(HttpServletRequest request) {
        System.out.println(request.getHeaderNames());
        System.out.println(request.getAttributeNames());
        System.out.println(request.getParameterNames());
        System.out.println(request.getParameterMap());

        return "Greetings from Spring Boot!";
    }
}
