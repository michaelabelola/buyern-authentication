package com.buyern.authentication.configs;

import com.buyern.authentication.notification.EmailRecipient;
import com.buyern.authentication.notification.NotificationModel;
import com.buyern.authentication.notification.NotificationType;
import com.buyern.authentication.notification.RecipientType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisPublishers {
    @Bean
    public Publisher notificationPublisher() {
        return new Publisher("notification");
    }

    public static class Publisher {

        private static String host = "194.35.120.40";
        private static int port = 6379;
        private final Jedis jedis;
        private final String channel;

        public Publisher(String channel) {
            this.jedis = new Jedis(host, port);
            this.channel = channel;
        }

        public void publish(String message) {
            jedis.publish(channel, message);
        }
        public void publish(Object message) {
            try {
                jedis.publish(channel, new ObjectMapper().writeValueAsString(message));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

