package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.DeliveryDTO;
import com.dto.delivery_tour_optimizer.mapper.DeliveryMapper;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/deliveries")
@Tag(name = "Deliveries", description = "Search and listing endpoints for deliveries")
@RequiredArgsConstructor
public class DeliverySearchController {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper = new DeliveryMapper();

    @GetMapping("/search")
    @Operation(summary = "Search deliveries", description = "Search deliveries by customer name, date range and minimum delay, with pagination")
    public Page<DeliveryDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long minDelay,
            Pageable pageable
    ) {
        Page<Delivery> page = deliveryRepository.searchDeliveries(name, startDate, endDate, minDelay, pageable);
        return page.map(deliveryMapper::toDTO);
    }
}
