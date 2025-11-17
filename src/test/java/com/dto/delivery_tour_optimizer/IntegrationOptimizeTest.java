package com.dto.delivery_tour_optimizer;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.*;
import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import com.dto.delivery_tour_optimizer.repository.*;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationOptimizeTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private WarehouseRepository warehouseRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private DeliveryRepository deliveryRepository;

    private Warehouse wh;
    private Vehicle vh;

    @BeforeEach
    void setup() {
        deliveryRepository.deleteAll();
        vehicleRepository.deleteAll();
        warehouseRepository.deleteAll();

        wh = new Warehouse();
        wh.setAddress("Main Warehouse, Paris");
        wh.setLatitude(48.8566);
        wh.setLongitude(2.3522);
        wh.setOpeningHours("06:00-22:00");
        warehouseRepository.save(wh);

        vh = new Vehicle();
        vh.setType(VehicleType.TRUCK);
        vh.setLicensePlate("AB-123-CD");
        vh.setMaxWeight(1000.0);
        vh.setMaxVolume(1000.0);
        vh.setMaxDeliveries(50);
        vehicleRepository.save(vh);

        for (int i=0;i<3;i++){
            Delivery d = new Delivery();
            d.setAddress("Addr"+i);
            d.setLatitude(48.85 + i*0.01);
            d.setLongitude(2.35 + i*0.01);
            d.setWeight(10.0);
            d.setVolume(5.0);
            d.setStatus(DeliveryStatus.PENDING);
            deliveryRepository.save(d);
        }
    }

    @Test
    void optimize_withNearestNeighbor_returns200() throws Exception {
        List<Long> ids = deliveryRepository.findAll().stream().map(Delivery::getId).toList();
        TourRequestDTO req = new TourRequestDTO();
        req.setDate(LocalDate.now());
        req.setVehicleId(vh.getId());
        req.setWarehouseId(wh.getId());
        req.setDeliveryIds(ids);
        req.setOptimizerType("NEAREST_NEIGHBOR");

        mockMvc.perform(post("/api/tours/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
