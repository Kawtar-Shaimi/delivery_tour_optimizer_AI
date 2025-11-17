package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import com.dto.delivery_tour_optimizer.service.VehicleService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    @Test
    public void testGetAllVehicles() {
        // Arrange
        VehicleRepository repository = mock(VehicleRepository.class);
        VehicleService service = new VehicleService(repository);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        // Act
        List<Vehicle> result = service.getAllVehicles();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetVehicleById() {
        // Arrange
        VehicleRepository repository = mock(VehicleRepository.class);
        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(vehicle));

        // Act
        Vehicle result = service.getVehicleById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetVehicleByIdNotFound() {
        // Arrange
        VehicleRepository repository = mock(VehicleRepository.class);
        VehicleService service = new VehicleService(repository);

        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            service.getVehicleById(999L);
        });
    }
}