package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.service.NearestNeighborOptimizer;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NearestNeighborOptimizerTest {

    @Test
    public void testCalculateOptimalTourWithEmptyList() {
        // Arrange
        NearestNeighborOptimizer optimizer = new NearestNeighborOptimizer();
        Warehouse warehouse = new Warehouse();
        Vehicle vehicle = new Vehicle();

        // Act
        List<Delivery> result = optimizer.calculateOptimalTour(Arrays.asList(), warehouse, vehicle);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCalculateOptimalTourWithOneDelivery() {
        // Arrange
        NearestNeighborOptimizer optimizer = new NearestNeighborOptimizer();

        Warehouse warehouse = new Warehouse();
        warehouse.setLatitude(0.0);
        warehouse.setLongitude(0.0);

        Vehicle vehicle = new Vehicle();

        Delivery delivery = new Delivery();
        delivery.setLatitude(1.0);
        delivery.setLongitude(1.0);

        // Act
        List<Delivery> result = optimizer.calculateOptimalTour(Arrays.asList(delivery), warehouse, vehicle);

        // Assert
        assertEquals(1, result.size());
        assertEquals(delivery, result.get(0));
    }
}