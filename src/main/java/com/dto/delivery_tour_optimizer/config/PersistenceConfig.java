package com.dto.delivery_tour_optimizer.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.dto.delivery_tour_optimizer.repository")
@EntityScan("com.dto.delivery_tour_optimizer.model")
public class PersistenceConfig {
}
