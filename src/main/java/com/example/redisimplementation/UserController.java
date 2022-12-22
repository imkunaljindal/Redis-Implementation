package com.example.redisimplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    RedisTemplate<String, User> redisTemplate;
    @Autowired
    ObjectMapper objectMapper;

    ////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/set_value")
    public void addValue(@RequestParam("key")String key, @RequestBody()User user){
        redisTemplate.opsForValue().set(key,user);
    }

    @GetMapping("/get_value")
    public User getValue(@RequestParam("key")String key){
        return (User)redisTemplate.opsForValue().get(key);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/lpush")
    public void addListValueLeft(@RequestParam("key")String key, @RequestBody()User user){
        redisTemplate.opsForList().leftPush(key,user);
    }

    @PostMapping("/rpush")
    public void addListValueRight(@RequestParam("key")String key, @RequestBody()User user){
        redisTemplate.opsForList().rightPush(key,user);
    }

    @GetMapping("/lpop")
    public User getListValue(@RequestParam("key")String key){
        return (User)redisTemplate.opsForList().leftPop(key);
    }

    //////////////////////////////////////////////////////////
    @PostMapping("/hmset")
    public void addHashValue(@RequestParam("key")String key,@RequestBody()User user){
        Map map = objectMapper.convertValue(user,Map.class);
        redisTemplate.opsForHash().putAll(key,map);
    }

    @GetMapping("/hget")
    public String getHashValue(@RequestParam("key") String key, @RequestParam("hashKey")String hashKey){
        return (String)redisTemplate.opsForHash().get(key,hashKey);
    }

    @GetMapping("/hgetall")
    public User getHashValueAll(@RequestParam("key")String key){
        Map map = redisTemplate.opsForHash().entries(key);
        User user = objectMapper.convertValue(map,User.class);
        return user;
    }
}
