package com.dto.delivery_tour_optimizer.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourRequestDTO {
    private LocalDate date;
    private Long vehicleId;
    private Long warehouseId;
    private List<Long> deliveryIds; // IDs des livraisons à optimiser
    private String optimizerType; // "NEAREST_NEIGHBOR" ou "CLARKE_WRIGHT"
}