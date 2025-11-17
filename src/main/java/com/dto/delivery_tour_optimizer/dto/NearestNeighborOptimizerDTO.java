package com.dto.delivery_tour_optimizer.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class NearestNeighborOptimizerDTO {
    private Long vehicleId;
    private Long warehouseId;
    private List<Long> deliveryIds;
}