package com.dto.delivery_tour_optimizer.mapper;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.Tour;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.model.Delivery;
import java.util.List;

public class TourMapper {

    public Tour toEntity(TourRequestDTO dto, Vehicle vehicle, Warehouse warehouse, List<Delivery> deliveries) {
        return Tour.builder()
                .date(dto.getDate())
                .vehicle(vehicle)
                .warehouse(warehouse)
                .deliveries(deliveries)
                .totalDistance(0.0) // Sera calculé lors de l'optimisation
                .build();
    }

    public TourRequestDTO toRequestDTO(Tour tour) {
        return TourRequestDTO.builder()
                .date(tour.getDate())
                .vehicleId(tour.getVehicle().getId())
                .warehouseId(tour.getWarehouse().getId())
                .deliveryIds(tour.getDeliveries().stream()
                        .map(Delivery::getId)
                        .toList())
                .optimizerType("CLARKE_WRIGHT") // Valeur par défaut
                .build();
    }
}