package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByAddress(String address);

    @Query("SELECT w FROM Warehouse w WHERE w.latitude BETWEEN :minLat AND :maxLat AND w.longitude BETWEEN :minLon AND :maxLon")
    List<Warehouse> findWarehousesInArea(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                         @Param("minLon") double minLon, @Param("maxLon") double maxLon);

    // Vérifier si une adresse existe déjà
    boolean existsByAddress(String address);

    // Trouver l'entrepôt le plus proche d'un point (pour les calculs d'optimisation)
    @Query("SELECT w FROM Warehouse w ORDER BY " +
            "SQRT((w.latitude - :latitude) * (w.latitude - :latitude) + " +
            "(w.longitude - :longitude) * (w.longitude - :longitude)) ASC")
    List<Warehouse> findNearestWarehouse(@Param("latitude") double latitude,
                                         @Param("longitude") double longitude);
}