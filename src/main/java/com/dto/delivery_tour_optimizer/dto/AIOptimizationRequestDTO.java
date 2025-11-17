package com.dto.delivery_tour_optimizer.dto;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.DeliveryHistory;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIOptimizationRequestDTO {
    private List<Delivery> deliveries;
    private List<DeliveryHistory> history;
    private Vehicle vehicle;
}
