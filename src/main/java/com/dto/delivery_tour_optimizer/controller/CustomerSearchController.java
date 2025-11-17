package com.dto.delivery_tour_optimizer.controller;

import com.dto.delivery_tour_optimizer.model.Customer;
import com.dto.delivery_tour_optimizer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Search endpoints for customers")
public class CustomerSearchController {

    private final CustomerRepository customerRepository;

    @GetMapping("/search")
    @Operation(summary = "Search customers", description = "Search customers by name (contains, ignore case) or preferred time slot, with pagination")
    public Page<Customer> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String timeSlot,
            Pageable pageable
    ) {
        if (name != null && !name.isBlank()) {
            return customerRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        if (timeSlot != null && !timeSlot.isBlank()) {
            return customerRepository.findByPreferredTimeSlot(timeSlot, pageable);
        }
        return customerRepository.findAll(pageable);
    }
}
