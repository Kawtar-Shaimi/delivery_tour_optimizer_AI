package com.dto.delivery_tour_optimizer.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDTO {
    private Long id;
    private LocalDate date;
    private double totalDistance;
    private Long vehicleId;
    private Long warehouseId;
    private List<DeliveryDTO> deliveries; // Liste ordonnée des livraisons
}