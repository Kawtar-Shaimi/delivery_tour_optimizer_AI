package com.dto.delivery_tour_optimizer.mapper;

import com.dto.delivery_tour_optimizer.dto.DeliveryDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Tour;

public class DeliveryMapper {

    public DeliveryDTO toDTO(Delivery delivery) {
        return DeliveryDTO.builder()
                .id(delivery.getId())
                .address(delivery.getAddress())
                .latitude(delivery.getLatitude())
                .longitude(delivery.getLongitude())
                .weight(delivery.getWeight())
                .volume(delivery.getVolume())
                .timeSlot(delivery.getTimeSlot())
                .status(delivery.getStatus())
                .tourId(delivery.getTour() != null ? delivery.getTour().getId() : null)
                .deliveryOrder(delivery.getDeliveryOrder())
                .build();
    }

    public Delivery toEntity(DeliveryDTO dto, Tour tour) {
        return Delivery.builder()
                .id(dto.getId())                    // ← CORRECTION: dto.getId() au lieu de delivery.getId()
                .address(dto.getAddress())          // ← CORRECTION: dto.getAddress()
                .latitude(dto.getLatitude())        // ← CORRECTION: dto.getLatitude()
                .longitude(dto.getLongitude())      // ← CORRECTION: dto.getLongitude()
                .weight(dto.getWeight())            // ← CORRECTION: dto.getWeight()
                .volume(dto.getVolume())            // ← CORRECTION: dto.getVolume()
                .timeSlot(dto.getTimeSlot())        // ← CORRECTION: dto.getTimeSlot()
                .status(dto.getStatus())            // ← CORRECTION: dto.getStatus()
                .tour(tour)
                .deliveryOrder(dto.getDeliveryOrder()) // ← CORRECTION: dto.getDeliveryOrder()
                .build();
    }
}