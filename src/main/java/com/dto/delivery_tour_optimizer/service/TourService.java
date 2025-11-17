package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.*;
import com.dto.delivery_tour_optimizer.repository.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
public class TourService {

    private static final Logger logger = Logger.getLogger(TourService.class.getName());

    private TourRepository tourRepository;
    private DeliveryRepository deliveryRepository;
    private VehicleRepository vehicleRepository;
    private WarehouseRepository warehouseRepository;
    private TourOptimizer nearestNeighborOptimizer;
    private TourOptimizer clarkeWrightOptimizer;

    // CONSTRUCTEUR CORRIGÉ
    public TourService(TourRepository tourRepository,
                       DeliveryRepository deliveryRepository,
                       VehicleRepository vehicleRepository,
                       WarehouseRepository warehouseRepository,
                       TourOptimizer nearestNeighborOptimizer,
                       TourOptimizer clarkeWrightOptimizer) {
        this.tourRepository = tourRepository;
        this.deliveryRepository = deliveryRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;

        logger.info("✅ TourService initialisé avec toutes les dépendances");
    }

    public List<Delivery> getOptimizedTour(TourRequestDTO request) {
        logger.info("🚀 Demande d'optimisation - Algorithme: " + request.getOptimizerType());

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> {
                    logger.severe("❌ Véhicule non trouvé - ID: " + request.getVehicleId());
                    return new RuntimeException("Véhicule non trouvé");
                });

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> {
                    logger.severe("❌ Entrepôt non trouvé - ID: " + request.getWarehouseId());
                    return new RuntimeException("Entrepôt non trouvé");
                });

        List<Delivery> deliveries = deliveryRepository.findAllById(request.getDeliveryIds());
        logger.info("📦 " + deliveries.size() + " livraisons à optimiser");

        // VÉRIFICATION DES CONTRAINTES
        if (deliveries.size() > vehicle.getMaxDeliveries()) {
            logger.warning("⚠️ Trop de livraisons: " + deliveries.size() + " > " + vehicle.getMaxDeliveries());
            throw new RuntimeException("Trop de livraisons pour ce véhicule. Maximum: " + vehicle.getMaxDeliveries());
        }

        double totalWeight = deliveries.stream().mapToDouble(Delivery::getWeight).sum();
        if (totalWeight > vehicle.getMaxWeight()) {
            logger.warning("⚠️ Poids trop élevé: " + totalWeight + "kg > " + vehicle.getMaxWeight() + "kg");
            throw new RuntimeException("Poids total trop élevé pour ce véhicule. Maximum: " + vehicle.getMaxWeight() + " kg");
        }

        double totalVolume = deliveries.stream().mapToDouble(Delivery::getVolume).sum();
        if (totalVolume > vehicle.getMaxVolume()) {
            logger.warning("⚠️ Volume trop élevé: " + totalVolume + "m³ > " + vehicle.getMaxVolume() + "m³");
            throw new RuntimeException("Volume total trop élevé pour ce véhicule. Maximum: " + vehicle.getMaxVolume() + " m³");
        }

        TourOptimizer optimizer = request.getOptimizerType().equals("NEAREST_NEIGHBOR")
                ? nearestNeighborOptimizer
                : clarkeWrightOptimizer;

        logger.info("⚡ Lancement de l'algorithme: " + request.getOptimizerType());
        List<Delivery> optimizedRoute = optimizer.calculateOptimalTour(deliveries, warehouse, vehicle);

        for (int i = 0; i < optimizedRoute.size(); i++) {
            optimizedRoute.get(i).setDeliveryOrder(i + 1);
        }

        logger.info("✅ Optimisation terminée - " + optimizedRoute.size() + " livraisons organisées");
        return optimizedRoute;
    }

    public double getTotalDistance(List<Delivery> route, Warehouse warehouse) {
        if (route.isEmpty()) return 0.0;

        double total = 0.0;
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        for (Delivery delivery : route) {
            total += DistanceUtils.calculateDistance(currentLat, currentLon,
                    delivery.getLatitude(), delivery.getLongitude());
            currentLat = delivery.getLatitude();
            currentLon = delivery.getLongitude();
        }

        total += DistanceUtils.calculateDistance(currentLat, currentLon,
                warehouse.getLatitude(), warehouse.getLongitude());

        logger.info("📏 Distance totale calculée: " + total + " km");
        return total;
    }

//    // Getters et Setters (inchangés)
//    public void setTourRepository(TourRepository tourRepository) {
//        this.tourRepository = tourRepository;
//    }
//
//    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
//        this.deliveryRepository = deliveryRepository;
//    }
//
//    public void setVehicleRepository(VehicleRepository vehicleRepository) {
//        this.vehicleRepository = vehicleRepository;
//    }
//
//    public void setWarehouseRepository(WarehouseRepository warehouseRepository) {
//        this.warehouseRepository = warehouseRepository;
//    }
//
//    public void setNearestNeighborOptimizer(TourOptimizer nearestNeighborOptimizer) {
//        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
//    }
//
//    public void setClarkeWrightOptimizer(TourOptimizer clarkeWrightOptimizer) {
//        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
//    }
}