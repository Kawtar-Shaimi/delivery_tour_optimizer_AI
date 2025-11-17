package com.dto.delivery_tour_optimizer.config;

import com.dto.delivery_tour_optimizer.model.*;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import com.dto.delivery_tour_optimizer.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;
    private final VehicleRepository vehicleRepository;
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public DataLoader(WarehouseRepository warehouseRepository,
                      VehicleRepository vehicleRepository,
                      DeliveryRepository deliveryRepository,
                      CustomerRepository customerRepository) {
        this.warehouseRepository = warehouseRepository;
        this.vehicleRepository = vehicleRepository;
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Chargement des données de test...");

        // Créer un entrepôt
        Warehouse warehouse = Warehouse.builder()
                .address("123 Main Street, City")
                .latitude(34.0522)
                .longitude(-118.2437)
                .openingHours("06:00-22:00")
                .build();
        warehouseRepository.save(warehouse);

        // Créer des véhicules
        Vehicle van = Vehicle.builder()
                .type(VehicleType.VAN)
                .licensePlate("VAN-001")
                .maxWeight(1000.0)
                .maxVolume(8.0)
                .maxDeliveries(50)
                .build();
        vehicleRepository.save(van);

        Vehicle bike = Vehicle.builder()
                .type(VehicleType.BIKE)
                .licensePlate("BIKE-001")
                .maxWeight(50.0)
                .maxVolume(0.5)
                .maxDeliveries(15)
                .build();
        vehicleRepository.save(bike);

        Vehicle truck = Vehicle.builder()
                .type(VehicleType.TRUCK)
                .licensePlate("TRUCK-001")
                .maxWeight(5000.0)
                .maxVolume(40.0)
                .maxDeliveries(100)
                .build();
        vehicleRepository.save(truck);

        // Créer des clients
        Customer customerA = Customer.builder()
                .name("Client A")
                .address("Client 1 Address")
                .latitude(34.0522 + 0.01)
                .longitude(-118.2437 + 0.01)
                .preferredTimeSlot("09:00-12:00")
                .build();

        Customer customerB = Customer.builder()
                .name("Client B")
                .address("Client 2 Address")
                .latitude(34.0522 + 0.02)
                .longitude(-118.2437 + 0.02)
                .preferredTimeSlot("13:00-16:00")
                .build();

        customerRepository.save(customerA);
        customerRepository.save(customerB);

        // Créer des livraisons de test
        for (int i = 1; i <= 5; i++) {
            Delivery delivery = Delivery.builder()
                    .address("Client " + i + " Address")
                    .latitude(34.0522 + (i * 0.01))
                    .longitude(-118.2437 + (i * 0.01))
                    .weight(5.0 * i)    // 5kg, 10kg, 15kg, 20kg, 25kg
                    .volume(0.2 * i)    // 0.2m³, 0.4m³, 0.6m³, 0.8m³, 1.0m³
                    .timeSlot(i % 2 == 0 ? customerB.getPreferredTimeSlot() : customerA.getPreferredTimeSlot())
                    .plannedTime(LocalTime.of(9 + i, 0))
                    .actualTime(LocalTime.of(9 + i, 15))
                    .customer(i % 2 == 0 ? customerB : customerA)
                    .build();
            deliveryRepository.save(delivery);
        }

        System.out.println("✅ Données de test chargées avec succès!");
        System.out.println("📦 Entrepôt créé avec ID: " + warehouse.getId());
        System.out.println("🚚 Véhicules créés: VAN, BIKE, TRUCK");
        System.out.println("📮 5 livraisons créées");
    }
}