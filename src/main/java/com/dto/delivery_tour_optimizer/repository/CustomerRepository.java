package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Backward compatibility for existing service method
    java.util.List<Customer> findByPreferredTimeSlot(String preferredTimeSlot);

    Page<Customer> findByPreferredTimeSlot(String preferredTimeSlot, Pageable pageable);

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}