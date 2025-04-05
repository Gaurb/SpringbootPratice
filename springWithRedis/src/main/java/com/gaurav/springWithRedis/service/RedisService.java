package com.gaurav.springWithRedis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

//    public void get(String key){
//        Object o=redisTemplate.opsForValue().get(key);
//        ObjectMapper mapper=new ObjectMapper();
//        return mapper.readValue(o.toString(), Entity.class );
//    }
}
