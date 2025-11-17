package com.dto.delivery_tour_optimizer.dto;

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
public class AIOptimizationResponseDTO {
    private List<Long> order;
    private List<String> recommendations;
    private List<RouteDTO> routes;
}
