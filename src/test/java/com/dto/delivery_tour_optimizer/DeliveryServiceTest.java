package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import com.dto.delivery_tour_optimizer.service.DeliveryService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryServiceTest {

    @Test
    public void testGetAllDeliveries() {
        // Arrange
        DeliveryRepository repository = mock(DeliveryRepository.class);
        DeliveryService service = new DeliveryService(repository);

        Delivery delivery1 = new Delivery();
        delivery1.setId(1L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(delivery1, delivery2));

        // Act
        List<Delivery> result = service.getAllDeliveries();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testDeleteDelivery() {
        // Arrange
        DeliveryRepository repository = mock(DeliveryRepository.class);
        DeliveryService service = new DeliveryService(repository);

        // Act
        service.deleteDelivery(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}