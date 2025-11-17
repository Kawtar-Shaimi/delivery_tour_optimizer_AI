package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.ClarkeWrightOptimizerDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import com.dto.delivery_tour_optimizer.service.ClarkeWrightOptimizer;
import com.dto.delivery_tour_optimizer.service.DistanceUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/clarke-wright")
public class ClarkeWrightOptimizerController {

    private static final Logger logger = Logger.getLogger(ClarkeWrightOptimizerController.class.getName());

    private final DeliveryRepository deliveryRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final ClarkeWrightOptimizer clarkeWrightOptimizer;

    public ClarkeWrightOptimizerController(DeliveryRepository deliveryRepository,
                                           VehicleRepository vehicleRepository,
                                           WarehouseRepository warehouseRepository,
                                           ClarkeWrightOptimizer clarkeWrightOptimizer) {
        this.deliveryRepository = deliveryRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
    }

    @PostMapping("/optimize")
    public ResponseEntity<String> optimize(@RequestBody ClarkeWrightOptimizerDTO request) {
        try {
            logger.info("🧪 Clarke & Wright Optimization - Livraisons: " + request.getDeliveryIds().size());

            // Récupération des données
            Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
            Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Entrepôt non trouvé"));
            List<Delivery> deliveries = deliveryRepository.findAllById(request.getDeliveryIds());

            if (deliveries.isEmpty()) {
                return ResponseEntity.badRequest().body("Aucune livraison trouvée");
            }

            // Exécution de l'algorithme
            long startTime = System.currentTimeMillis();
            List<Delivery> optimizedRoute = clarkeWrightOptimizer.calculateOptimalTour(deliveries, warehouse, vehicle);
            long endTime = System.currentTimeMillis();

            // Calcul de la distance
            double totalDistance = calculateTotalDistance(optimizedRoute, warehouse);

            // Construction du résultat
            StringBuilder result = new StringBuilder();
            result.append("✅ CLARKE & WRIGHT OPTIMIZATION SUCCESS\n");
            result.append("📊 Résultats:\n");
            result.append("• Livraisons traitées: ").append(optimizedRoute.size()).append("\n");
            result.append("• Distance totale: ").append(String.format("%.2f", totalDistance)).append(" km\n");
            result.append("• Temps d'exécution: ").append(endTime - startTime).append(" ms\n");
            result.append("• Ordre optimisé:\n");

            for (int i = 0; i < optimizedRoute.size(); i++) {
                Delivery delivery = optimizedRoute.get(i);
                result.append("  ").append(i + 1).append(". ").append(delivery.getAddress())
                        .append(" (Lat: ").append(delivery.getLatitude())
                        .append(", Lon: ").append(delivery.getLongitude()).append(")\n");
            }

            logger.info("✅ Clarke & Wright Réussi - Distance: " + totalDistance + " km");
            return ResponseEntity.ok(result.toString());

        } catch (Exception e) {
            logger.severe("❌ Clarke & Wright Échec: " + e.getMessage());
            return ResponseEntity.badRequest().body("❌ Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("✅ Clarke & Wright Controller is UP and RUNNING");
    }

    private double calculateTotalDistance(List<Delivery> route, Warehouse warehouse) {
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

        return total;
    }
}