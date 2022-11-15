package com.buyern.authentication.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Slf4j
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private static String host;
    @Value("${spring.redis.port}")
    private static int port;

    public static Jedis jedis() {
        return new Jedis(host, port);
    }
}
