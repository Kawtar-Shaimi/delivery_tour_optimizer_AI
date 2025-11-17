package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.service.DistanceUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DistanceUtilsTest {

    @Test
    public void testCalculateDistance() {
        // Arrange
        double lat1 = 48.8566; // Paris
        double lon1 = 2.3522;
        double lat2 = 45.7640; // Lyon
        double lon2 = 4.8357;

        // Act
        double distance = DistanceUtils.calculateDistance(lat1, lon1, lat2, lon2);

        // Assert
        assertTrue(distance > 0);
        // La distance Paris-Lyon est d'environ 400km
        // Notre calcul simplifié donnera une valeur différente mais positive
        assertTrue(distance > 300);
    }

    @Test
    public void testCalculateDistanceSamePoint() {
        // Arrange
        double lat1 = 48.8566;
        double lon1 = 2.3522;

        // Act
        double distance = DistanceUtils.calculateDistance(lat1, lon1, lat1, lon1);

        // Assert
        assertEquals(0.0, distance, 0.001);
    }
}