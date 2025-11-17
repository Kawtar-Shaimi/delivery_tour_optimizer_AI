package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import org.springframework.stereotype.Component; // ← AJOUT

import java.util.*;
import java.util.logging.Logger;

@Component("nearestNeighborOptimizer")
public class NearestNeighborOptimizer implements TourOptimizer {


    private static final Logger logger = Logger.getLogger(NearestNeighborOptimizer.class.getName());

    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        logger.info("🚀 Début de l'optimisation Nearest Neighbor - " + deliveries.size() + " livraisons");

        if (deliveries.isEmpty()) {
            logger.warning("⚠️ Liste de livraisons vide");
            return new ArrayList<>();
        }

        List<Delivery> result = new ArrayList<>();
        List<Delivery> remaining = new ArrayList<>(deliveries);

        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            Delivery nearest = findNearest(currentLat, currentLon, remaining);
            result.add(nearest);
            remaining.remove(nearest);
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        logger.info("✅ Optimisation Nearest Neighbor terminée - " + result.size() + " livraisons organisées");
        return result;
    }

    private Delivery findNearest(double currentLat, double currentLon, List<Delivery> deliveries) {
        Delivery nearest = deliveries.get(0);
        double minDistance = DistanceUtils.calculateDistance(currentLat, currentLon,
                nearest.getLatitude(), nearest.getLongitude());

        for (Delivery delivery : deliveries) {
            double distance = DistanceUtils.calculateDistance(currentLat, currentLon,
                    delivery.getLatitude(), delivery.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = delivery;
            }
        }

        return nearest;
    }
}