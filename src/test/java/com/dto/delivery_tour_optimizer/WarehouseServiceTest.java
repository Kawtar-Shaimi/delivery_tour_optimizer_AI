package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import com.dto.delivery_tour_optimizer.service.WarehouseService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WarehouseServiceTest {

    @Test
    public void testGetAllWarehouses() {
        // Arrange
        WarehouseRepository repository = mock(WarehouseRepository.class);
        WarehouseService service = new WarehouseService(repository);

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(warehouse1, warehouse2));

        // Act
        List<Warehouse> result = service.getAllWarehouses();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSaveWarehouse() {
        // Arrange
        WarehouseRepository repository = mock(WarehouseRepository.class);
        WarehouseService service = new WarehouseService(repository);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);

        when(repository.save(warehouse)).thenReturn(warehouse);

        // Act
        Warehouse result = service.saveWarehouse(warehouse);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(warehouse);
    }
}