package com.dto.delivery_tour_optimizer.dto;

import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDTO {
    private Long id;
    private String address;
    private double latitude;
    private double longitude;
    private double weight;
    private double volume;
    private String timeSlot;
    private String plannedTime;
    private String actualTime;
    private DeliveryStatus status;
    private Long tourId;
    private Long customerId;
    private Integer deliveryOrder; // Ordre dans la tournée
}