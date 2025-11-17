package com.dto.delivery_tour_optimizer.mapper;

import com.dto.delivery_tour_optimizer.dto.WarehouseDTO;
import com.dto.delivery_tour_optimizer.model.Warehouse;

public class WarehouseMapper {

    public WarehouseDTO toDTO(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .address(warehouse.getAddress())
                .latitude(warehouse.getLatitude())
                .longitude(warehouse.getLongitude())
                .openingHours(warehouse.getOpeningHours())
                .build();
    }

    public Warehouse toEntity(WarehouseDTO dto) {
        return Warehouse.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .openingHours(dto.getOpeningHours())
                .build();
    }
}