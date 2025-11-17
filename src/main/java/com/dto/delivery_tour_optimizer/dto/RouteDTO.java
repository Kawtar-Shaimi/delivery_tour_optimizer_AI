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
public class RouteDTO {
    private Long fromId;
    private Long toId;
    private String reason;
}
