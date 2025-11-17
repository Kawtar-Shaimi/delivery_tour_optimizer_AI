package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.repository.*;
import com.dto.delivery_tour_optimizer.service.ClarkeWrightOptimizer;
import com.dto.delivery_tour_optimizer.service.NearestNeighborOptimizer;
import com.dto.delivery_tour_optimizer.service.TourService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourServiceTest {

    @Test
    public void testGetTotalDistanceEmptyRoute() {
        // Arrange
        TourService service = new TourService(
                mock(TourRepository.class),
                mock(DeliveryRepository.class),
                mock(VehicleRepository.class),
                mock(WarehouseRepository.class),
                mock(NearestNeighborOptimizer.class),
                mock(ClarkeWrightOptimizer.class)
        );

        Warehouse warehouse = new Warehouse();

        // Act
        double distance = service.getTotalDistance(Arrays.asList(), warehouse);

        // Assert
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    public void testGetTotalDistanceWithDeliveries() {
        // Arrange
        TourService service = new TourService(
                mock(TourRepository.class),
                mock(DeliveryRepository.class),
                mock(VehicleRepository.class),
                mock(WarehouseRepository.class),
                mock(NearestNeighborOptimizer.class),
                mock(ClarkeWrightOptimizer.class)
        );

        Warehouse warehouse = new Warehouse();
        warehouse.setLatitude(0.0);
        warehouse.setLongitude(0.0);

        Delivery delivery1 = new Delivery();
        delivery1.setLatitude(1.0);
        delivery1.setLongitude(1.0);

        Delivery delivery2 = new Delivery();
        delivery2.setLatitude(2.0);
        delivery2.setLongitude(2.0);

        // Act
        double distance = service.getTotalDistance(Arrays.asList(delivery1, delivery2), warehouse);

        // Assert
        assertTrue(distance > 0);
    }
}