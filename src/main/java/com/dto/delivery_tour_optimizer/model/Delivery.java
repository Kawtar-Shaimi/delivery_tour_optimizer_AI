package com.dto.delivery_tour_optimizer.model;

import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double latitude;
    private double longitude;
    private double weight;    // en kg
    private double volume;    // en m³
    private String timeSlot;  // ex: "09:00-11:00"
    private LocalTime plannedTime;
    private LocalTime actualTime;

    @Column(name = "delivery_order")
    private Integer deliveryOrder; // Ordre dans la tournée

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (customer != null) {
            this.address = customer.getAddress();
            this.latitude = customer.getLatitude();
            this.longitude = customer.getLongitude();
            if (customer.getPreferredTimeSlot() != null) {
                this.timeSlot = customer.getPreferredTimeSlot();
            }
        }
    }
}