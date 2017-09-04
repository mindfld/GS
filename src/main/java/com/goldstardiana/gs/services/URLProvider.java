package com.goldstardiana.gs.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class URLProvider {
    private Map<String,String> urls = new HashMap<>();

    public URLProvider() {
        this.urls = new HashMap<>();
        urls.put("Тестовый видеотренинг","http://bit.ly/2wwRCxP");
    }

    public String getUrlForProduct(String productId){
        return urls.get(productId);
    }
}
