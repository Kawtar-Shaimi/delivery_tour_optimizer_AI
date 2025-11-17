package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.DeliveryHistory;
import com.dto.delivery_tour_optimizer.model.Tour;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.model.enums.TourStatus;
import com.dto.delivery_tour_optimizer.repository.DeliveryHistoryRepository;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import com.dto.delivery_tour_optimizer.repository.TourRepository;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service pour l'optimisation des tournées de livraison
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TourService {

    private static final Logger logger = Logger.getLogger(TourService.class.getName());

    private final TourRepository tourRepository;
    private final DeliveryRepository deliveryRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final @Qualifier("nearestNeighborOptimizer") TourOptimizer nearestNeighborOptimizer;
    private final @Qualifier("clarkeWrightOptimizer") TourOptimizer clarkeWrightOptimizer;
    private final @Qualifier("aiOptimizer") TourOptimizer aiOptimizer;
    private final DeliveryHistoryRepository deliveryHistoryRepository;

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

        String type = request.getOptimizerType() == null ? "" : request.getOptimizerType().toUpperCase();
        TourOptimizer optimizer = switch (type) {
            case "AI" -> aiOptimizer;
            case "NEAREST_NEIGHBOR" -> nearestNeighborOptimizer;
            case "CLARKE_WRIGHT" -> clarkeWrightOptimizer;
            default -> nearestNeighborOptimizer;
        };

        logger.info("⚡ Lancement de l'algorithme: " + request.getOptimizerType());
        List<Delivery> optimizedRoute = optimizer.calculateOptimalTour(deliveries, warehouse, vehicle);

        for (int i = 0; i < optimizedRoute.size(); i++) {
            optimizedRoute.get(i).setDeliveryOrder(i + 1);
        }

        logger.info("✅ Optimisation terminée - " + optimizedRoute.size() + " livraisons organisées");
        return optimizedRoute;
    }

    public Tour updateTourStatus(Long tourId, TourStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Tour status must not be null");
        }

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + tourId));

        TourStatus previousStatus = tour.getStatus();
        tour.setStatus(newStatus);

        if (newStatus == TourStatus.COMPLETED && previousStatus != TourStatus.COMPLETED) {
            createHistoriesForCompletedTour(tour);
        }

        return tourRepository.save(tour);
    }

    private void createHistoriesForCompletedTour(Tour tour) {
        LocalDate deliveryDate = tour.getDate() != null ? tour.getDate() : LocalDate.now();
        List<DeliveryHistory> histories = new ArrayList<>();

        for (Delivery delivery : tour.getDeliveries()) {
            DeliveryHistory history = DeliveryHistory.builder()
                    .tour(tour)
                    .delivery(delivery)
                    .customer(delivery.getCustomer())
                    .deliveryDate(deliveryDate)
                    .plannedTime(delivery.getPlannedTime())
                    .actualTime(delivery.getActualTime())
                    .delayMinutes(calculateDelay(delivery.getPlannedTime(), delivery.getActualTime()))
                    .dayOfWeek(deliveryDate.getDayOfWeek())
                    .build();
            histories.add(history);
        }

        if (!histories.isEmpty()) {
            deliveryHistoryRepository.saveAll(histories);
        }
    }

    private long calculateDelay(LocalTime planned, LocalTime actual) {
        if (planned == null || actual == null) {
            return 0L;
        }
        return Duration.between(planned, actual).toMinutes();
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
}