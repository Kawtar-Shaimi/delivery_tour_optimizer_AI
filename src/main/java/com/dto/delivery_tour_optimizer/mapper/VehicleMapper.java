package com.dto.delivery_tour_optimizer.mapper;

import com.dto.delivery_tour_optimizer.dto.VehicleDTO;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;

public class VehicleMapper {

    public VehicleDTO toDTO(Vehicle vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .type(vehicle.getType())
                .licensePlate(vehicle.getLicensePlate())
                .maxWeight(vehicle.getMaxWeight())
                .maxVolume(vehicle.getMaxVolume())
                .maxDeliveries(vehicle.getMaxDeliveries())
                .build();
    }

    public Vehicle toEntity(VehicleDTO dto) {
        return Vehicle.builder()
                .id(dto.getId())
                .type(dto.getType())
                .licensePlate(dto.getLicensePlate())
                .maxWeight(dto.getMaxWeight())
                .maxVolume(dto.getMaxVolume())
                .maxDeliveries(dto.getMaxDeliveries())
                .build();
    }

    // Méthode utilitaire pour créer un véhicule avec des contraintes par défaut selon le type
    public Vehicle createVehicleWithConstraints(VehicleType type, String licensePlate) {
        return switch (type) {
            case BIKE -> Vehicle.builder()
                    .type(VehicleType.BIKE)
                    .licensePlate(licensePlate)
                    .maxWeight(50.0)
                    .maxVolume(0.5)
                    .maxDeliveries(15)
                    .build();
            case VAN -> Vehicle.builder()
                    .type(VehicleType.VAN)
                    .licensePlate(licensePlate)
                    .maxWeight(1000.0)
                    .maxVolume(8.0)
                    .maxDeliveries(50)
                    .build();
            case TRUCK -> Vehicle.builder()
                    .type(VehicleType.TRUCK)
                    .licensePlate(licensePlate)
                    .maxWeight(5000.0)
                    .maxVolume(40.0)
                    .maxDeliveries(100)
                    .build();
        };
    }
}