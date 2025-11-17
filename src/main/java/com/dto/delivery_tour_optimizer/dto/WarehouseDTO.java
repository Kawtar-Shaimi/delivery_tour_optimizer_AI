package com.dto.delivery_tour_optimizer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {
    private Long id;
    private String address;
    private double latitude;
    private double longitude;
    private String openingHours;
}