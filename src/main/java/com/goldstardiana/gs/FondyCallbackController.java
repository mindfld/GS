package com.goldstardiana.gs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class FondyCallbackController {
    @RequestMapping("/zaza")
    public String index(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            sb.append(headerNames.nextElement()).append("\n");
        }

        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            sb.append(attributeNames.nextElement()).append("\n");
        }

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            sb.append(paramNames.nextElement()).append("\n");
        }
        sb.append(request.getParameterMap());
        return sb.toString();
    }
}
