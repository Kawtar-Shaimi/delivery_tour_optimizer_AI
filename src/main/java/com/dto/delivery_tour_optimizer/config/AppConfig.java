package com.dto.delivery_tour_optimizer.config;

import com.dto.delivery_tour_optimizer.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public NearestNeighborOptimizer nearestNeighborOptimizer(){
        return new NearestNeighborOptimizer();
    }

    @Bean
    public ClarkeWrightOptimizer clarkeWrightOptimizer(){
        return new ClarkeWrightOptimizer();
    }
}
