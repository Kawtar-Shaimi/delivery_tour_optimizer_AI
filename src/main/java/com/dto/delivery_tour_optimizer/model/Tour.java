package com.dto.delivery_tour_optimizer.model;

import com.dto.delivery_tour_optimizer.model.enums.TourStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double totalDistance; // en km

    @Enumerated(EnumType.STRING)
    private TourStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @OrderBy("deliveryOrder ASC")
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();
}