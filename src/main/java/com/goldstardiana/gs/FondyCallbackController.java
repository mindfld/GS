package com.goldstardiana.gs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FondyCallbackController {
    @RequestMapping("/zaza")
    public String index(HttpServletRequest request) {

        return String.valueOf(request.getHeaderNames()) +
                request.getAttributeNames() +
                request.getParameterNames() +
                request.getParameterMap();
    }
}
