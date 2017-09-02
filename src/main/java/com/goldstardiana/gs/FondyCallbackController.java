package com.goldstardiana.gs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@RestController
public class FondyCallbackController {
    @RequestMapping("/zaza")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            sb.append(headerNames.nextElement()).append("\n");
        }
        sb.append("\n").append("\n");

        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            sb.append(attributeNames.nextElement()).append("\n");
        }
        sb.append("\n").append("\n");

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            sb.append(paramNames.nextElement()).append("\n");
        }
        sb.append("\n").append("\n");

        sb.append(request.getParameterMap());
        sb.append("\n").append("\n");

        System.out.println("------------------REQUESTAAA!!!!--------------");
        System.out.println(request.toString());
        System.out.println("------------------END OF MESSSAGA!!!!--------------");

        System.out.println("------------------RESPONSAAA!!!!--------------");
        System.out.println(response.toString());
        System.out.println("------------------END OF MESSSAGA!!!!--------------");
        return sb.toString();
    }
}
