package com.lzy.myrule;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRule {
    @Bean
    public RandomRule getMyRule(){
        return new RandomRule();
    }
}
