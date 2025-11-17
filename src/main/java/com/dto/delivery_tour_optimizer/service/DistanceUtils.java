package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Warehouse;

public class DistanceUtils {

    // Version simplifiée - pas besoin de formule compliquée pour le début
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Distance approximative (assez précise pour des distances courtes)
        double x = lat2 - lat1;
        double y = lon2 - lon1;
        return Math.sqrt(x * x + y * y) * 111; // 111km par degré
    }

    public static double distanceToWarehouse(Delivery delivery, Warehouse warehouse) {
        return calculateDistance(delivery.getLatitude(), delivery.getLongitude(),
                warehouse.getLatitude(), warehouse.getLongitude());
    }

    public static double distanceBetween(Delivery d1, Delivery d2) {
        return calculateDistance(d1.getLatitude(), d1.getLongitude(),
                d2.getLatitude(), d2.getLongitude());
    }
}