package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByTourId(Long tourId);

    List<Delivery> findByTourIsNull();

    List<Delivery> findByTimeSlot(String timeSlot);

    List<Delivery> findByStatusAndTourId(DeliveryStatus status, Long tourId);

    // Compter les livraisons par statut
    long countByStatus(DeliveryStatus status);

    @Query("select d from Delivery d join DeliveryHistory h on h.delivery = d " +
            "where (:name is null or lower(d.customer.name) like lower(concat('%',:name,'%'))) " +
            "and (:startDate is null or h.deliveryDate >= :startDate) " +
            "and (:endDate is null or h.deliveryDate <= :endDate) " +
            "and (:minDelay is null or h.delayMinutes >= :minDelay)")
    Page<Delivery> searchDeliveries(
            @Param("name") String name,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minDelay") Long minDelay,
            Pageable pageable);
}