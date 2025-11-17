package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import java.util.*;
import java.util.logging.Logger;

public class ClarkeWrightOptimizer implements TourOptimizer {

    // Logger simple
    private static final Logger logger = Logger.getLogger(ClarkeWrightOptimizer.class.getName());

    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        logger.info("🚀 Début de l'optimisation Clarke & Wright - " + deliveries.size() + " livraisons");

        if (deliveries.isEmpty()) {
            logger.warning("⚠️ Liste de livraisons vide");
            return new ArrayList<>();
        }

        // APPROCHE SIMPLIFIÉE
        logger.info("📊 Calcul des économies pour " + deliveries.size() + " livraisons");

        List<DeliveryPair> pairs = new ArrayList<>();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Delivery d1 = deliveries.get(i);
                Delivery d2 = deliveries.get(j);

                double saving = DistanceUtils.distanceToWarehouse(d1, warehouse)
                        + DistanceUtils.distanceToWarehouse(d2, warehouse)
                        - DistanceUtils.distanceBetween(d1, d2);

                pairs.add(new DeliveryPair(d1, d2, saving));
            }
        }

        logger.info("📈 " + pairs.size() + " paires d'économies calculées");

        pairs.sort((p1, p2) -> Double.compare(p2.saving, p1.saving));

        List<Delivery> result = buildSimpleRoute(pairs, deliveries);
        logger.info("✅ Optimisation Clarke & Wright terminée - " + result.size() + " livraisons organisées");
        return result;
    }

    private List<Delivery> buildSimpleRoute(List<DeliveryPair> pairs, List<Delivery> allDeliveries) {
        if (pairs.isEmpty()) return new ArrayList<>(allDeliveries);

        DeliveryPair bestPair = pairs.get(0);
        List<Delivery> route = new ArrayList<>();
        route.add(bestPair.delivery1);
        route.add(bestPair.delivery2);

        Set<Delivery> added = new HashSet<>();
        added.add(bestPair.delivery1);
        added.add(bestPair.delivery2);

        for (DeliveryPair pair : pairs) {
            if (!added.contains(pair.delivery1)) {
                route.add(pair.delivery1);
                added.add(pair.delivery1);
            }
            if (!added.contains(pair.delivery2)) {
                route.add(pair.delivery2);
                added.add(pair.delivery2);
            }
        }

        return route;
    }

    private static class DeliveryPair {
        Delivery delivery1;
        Delivery delivery2;
        double saving;

        DeliveryPair(Delivery d1, Delivery d2, double saving) {
            this.delivery1 = d1;
            this.delivery2 = d2;
            this.saving = saving;
        }
    }
}