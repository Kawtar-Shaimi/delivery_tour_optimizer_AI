package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByTourId(Long tourId);

    List<Delivery> findByTourIsNull();

    List<Delivery> findByTimeSlot(String timeSlot);

    List<Delivery> findByStatusAndTourId(DeliveryStatus status, Long tourId);

    // Compter les livraisons par statut
    long countByStatus(DeliveryStatus status);
}