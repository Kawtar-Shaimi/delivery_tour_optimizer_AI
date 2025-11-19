package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.dto.DeliveryDTO;
import com.dto.delivery_tour_optimizer.dto.TourRequestDTO;
import com.dto.delivery_tour_optimizer.mapper.DeliveryMapper;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.service.TourService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    @Operation(summary = "Optimize a tour (paginated)", description = "Optimize and return a Page with pageable metadata")
    public Page<DeliveryDTO> optimizeTour(@RequestBody TourRequestDTO request,
                                          @ParameterObject Pageable pageable) {
        List<Delivery> route = tourService.getOptimizedTour(request);
        DeliveryMapper mapper = new DeliveryMapper();
        List<DeliveryDTO> all = route.stream().map(mapper::toDTO).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), all.size());
        List<DeliveryDTO> pageContent = start >= all.size() ? java.util.List.of() : all.subList(start, end);
        return new PageImpl<>(pageContent, pageable, all.size());
    }

    @GetMapping("/test")
    @Operation(summary = "Health check", description = "Simple endpoint to verify Tours API is available")
    public String test() {
        return "✅ Tour API is working!";
    }
}