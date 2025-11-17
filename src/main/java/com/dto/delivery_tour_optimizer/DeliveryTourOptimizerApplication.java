package com.dto.delivery_tour_optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// CETTE ANNOTATION EST OBLIGATOIRE POUR SPRING BOOT
// ELLE NE CONFIGURE PAS VOS BEANS MAIS ACTIVE SPRING BOOT
@SpringBootApplication
public class DeliveryTourOptimizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryTourOptimizerApplication.class, args);
    }
}