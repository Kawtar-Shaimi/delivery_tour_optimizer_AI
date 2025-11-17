package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.DeliveryDTO;
import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.mapper.DeliveryMapper;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.service.TourService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tours", description = "Endpoints for optimizing delivery tours")
public class TourController {

    private final TourService tourService;

    @PostMapping("/optimize")
    @Operation(summary = "Optimize a tour", description = "Optimize the tour for given deliveries, warehouse and vehicle using selected optimizer")
    public List<DeliveryDTO> optimizeTour(@RequestBody TourRequestDTO request) {
        List<Delivery> route = tourService.getOptimizedTour(request);
        DeliveryMapper mapper = new DeliveryMapper();
        return route.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/test")
    @Operation(summary = "Health check", description = "Simple endpoint to verify Tours API is available")
    public String test() {
        return "✅ Tour API is working!";
    }
}