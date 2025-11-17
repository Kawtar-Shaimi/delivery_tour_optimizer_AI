package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByDate(LocalDate date);

    List<Tour> findByVehicleId(Long vehicleId);

    List<Tour> findByWarehouseId(Long warehouseId);

    List<Tour> findByDateAndVehicleId(LocalDate date, Long vehicleId);

    @Query("SELECT t FROM Tour t ORDER BY t.totalDistance DESC")
    List<Tour> findToursOrderByTotalDistanceDesc();

    long countByDate(LocalDate date);

    List<Tour> findByDateBetween(LocalDate startDate, LocalDate endDate);
}