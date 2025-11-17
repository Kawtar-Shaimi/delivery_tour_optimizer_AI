package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByType(VehicleType type);

    Vehicle findByLicensePlate(String licensePlate);

    @Query("SELECT v FROM Vehicle v WHERE v.maxWeight >= :minWeight AND v.maxVolume >= :minVolume")
    List<Vehicle> findVehiclesWithMinCapacity(@Param("minWeight") double minWeight,
                                              @Param("minVolume") double minVolume);

    boolean existsByLicensePlate(String licensePlate);

    // Trouver les véhicules pouvant transporter un certain nombre de livraisons
    List<Vehicle> findByMaxDeliveriesGreaterThanEqual(int minDeliveries);
}