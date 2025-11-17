package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private TourService tourService;

    // Constructeur avec injection
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/optimize")
    public List<Delivery> optimizeTour(@RequestBody TourRequestDTO request) {
        return tourService.getOptimizedTour(request);
    }

    @GetMapping("/test")
    public String test() {
        return "✅ Tour API is working!";
    }
}