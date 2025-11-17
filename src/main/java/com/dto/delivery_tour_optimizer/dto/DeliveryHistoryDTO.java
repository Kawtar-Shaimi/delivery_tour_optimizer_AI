package com.dto.delivery_tour_optimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryHistoryDTO {
    private Long id;
    private Long deliveryId;
    private Long customerId;
    private Long tourId;
    private String deliveryDate;
    private String plannedTime;
    private String actualTime;
    private Long delayMinutes;
    private String dayOfWeek;
}
