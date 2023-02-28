package com.example.newsapp.service.impl;

import com.example.newsapp.service.i18nService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class i18nServiceImpl implements i18nService {

    private Map<String, ResourceBundle> messageSources;

    @PostConstruct
    public void postConstruct(){
        messageSources = new LinkedHashMap<>();
        messageSources.put("kk", getMessageSource("kk"));
        messageSources.put("ru", getMessageSource("ru"));
    }

    private ResourceBundle getMessageSource(String locale){
        try{
            InputStream stream = getClass().getClassLoader().getResourceAsStream("i18n/messages_" + locale + ".properties");
            if(stream == null){
                throw new RuntimeException("Not found messages_" + locale + ".properties");
            }

            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return new PropertyResourceBundle(reader);
        }catch (IOException e){
            log.error("Error load file", e);
        }
        return null;
    }

    @Override
    public Map<String, String> getMessages(String key, Object... values) {
        Map<String,String> messages = new LinkedHashMap<>();
        for(Map.Entry<String, ResourceBundle> messageSource : this.messageSources.entrySet()){
            try{
                if(values !=null && values.length >0){
                    messages.put(messageSource.getKey(), String.format(messageSource.getValue().getString(key) + " {0}", values));
                }else{
                    messages.put(messageSource.getKey(), messageSource.getValue().getString(key));
                }
            }catch (MissingResourceException e) {
                log.error("Not found by key " + key);
                throw e;
            }
        }
        return messages;
    }

}
