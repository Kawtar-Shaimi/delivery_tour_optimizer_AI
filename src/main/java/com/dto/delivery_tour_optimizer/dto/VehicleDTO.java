package com.dto.delivery_tour_optimizer.dto;

import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private VehicleType type;
    private String licensePlate;
    private double maxWeight;
    private double maxVolume;
    private int maxDeliveries;
}