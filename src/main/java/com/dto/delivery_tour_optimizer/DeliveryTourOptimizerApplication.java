package com.dto.delivery_tour_optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

// CETTE ANNOTATION EST OBLIGATOIRE POUR SPRING BOOT
// ELLE NE CONFIGURE PAS VOS BEANS MAIS ACTIVE SPRING BOOT
@SpringBootApplication
// CETTE ANNOTATION CHARGE LE FICHIER XML - C'EST LA SEULE SOLUTION
@ImportResource("classpath:applicationContext.xml")
public class DeliveryTourOptimizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryTourOptimizerApplication.class, args);
    }
}