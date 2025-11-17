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
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String preferredTimeSlot;
}
