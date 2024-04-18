package com.api.swagger3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    /*
     * RedisConnectionFactory는 새로운 Connection이나 이미 존재하는 Connection을 리턴합니다.
     * 예외 변환도 가능하며 예외 발생 시 SpringDataAccessException으로 전환합니다.
     * Jedis, Lettuce 등등 Redis-client를 선택해서 연결을 생성할 수 있습니다.
     * @ConditionalOnProperty(name = "redis.enabled", havingValue = "true", matchIfMissing = true)는 Redis서버가 설정되지 않았을때 활성화 하지 않게 하기 위해 사용
     */
    @Bean
    @ConditionalOnProperty(name = "redis.enabled", havingValue = "true", matchIfMissing = true)
    public RedisConnectionFactory redisConnectionFactory(){
        System.out.println("redisHost : "+redisHost);
        System.out.println("redisPort : "+redisPort);
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /*
     * StringRedisSerializer를 사용하고, 저장된 key-value를 읽을 수 있습니다.
     * key-value가 String 형태로 나오게 하기 위해 StringRedisTemplate를 사용합니다.
     * thread-safe하고 재사용이 용이합니다. 
     */

    @Bean
    @ConditionalOnProperty(name = "redis.enabled", havingValue = "true", matchIfMissing = true)
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    
    @Bean
    @ConditionalOnProperty(name = "redis.enabled", havingValue = "true", matchIfMissing = true)
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate  stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        return stringRedisTemplate;
    }
}
