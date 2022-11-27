//package com.inbank.creditdecisionengine.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//
////    @Value("${redis.hostName}")
////    private String HOST_NAME;
////
////    @Value("${redis.port}")
////    private String PORT;
//
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory(
//            @Value("${spring.cache.redis.host}") final String host,
//            @Value("${spring.cache.redis.port}") final int port) {
//        return new LettuceConnectionFactory(host, port);
//    }
//
//    @Bean
//    public RedisTemplate<?, ?> redisTemplate(final LettuceConnectionFactory lettuceConnectionFactory) {
//        final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//
//
////    @Bean
////    @Primary
////    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
////        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
////                .useSsl().build();
////        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
////    }
////
////    @Bean
////    public RedisConfiguration defaultRedisConfig() {
////        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
////        config.setHostName("localhost");
//////        config.setPassword(RedisPassword.of("123"));
////        return config;
////    }
//}
