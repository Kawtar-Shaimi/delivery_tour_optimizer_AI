package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Customer;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.repository.CustomerRepository;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    public Delivery saveDelivery(Delivery delivery) {
        if (delivery.getCustomer() != null && delivery.getCustomer().getId() != null) {
            Customer customer = customerRepository.findById(delivery.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + delivery.getCustomer().getId()));
            delivery.setCustomer(customer);
        }
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}