package com.dto.delivery_tour_optimizer.config;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;

    public DataInitializer(VehicleRepository vehicleRepository,
                           WarehouseRepository warehouseRepository,
                           DeliveryRepository deliveryRepository) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Créer un véhicule
        Vehicle van = Vehicle.builder()
                .type(VehicleType.VAN)
                .licensePlate("VAN-001")
                .maxWeight(1000.0)
                .maxVolume(8.0)
                .maxDeliveries(50)
                .build();
        Vehicle savedVehicle = vehicleRepository.save(van);

        // Créer un entrepôt
        Warehouse warehouse = Warehouse.builder()
                .address("123 Main Street, Paris")
                .latitude(48.8566)
                .longitude(2.3522)
                .openingHours("06:00-22:00")
                .build();
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        // Créer des livraisons de test
        Delivery delivery1 = Delivery.builder()
                .address("10 Rue de Paris, 75001 Paris")
                .latitude(48.8584)
                .longitude(2.2945)
                .weight(5.0)
                .volume(0.1)
                .timeSlot("09:00-11:00")
                .status(DeliveryStatus.PENDING)
                .build();

        Delivery delivery2 = Delivery.builder()
                .address("25 Avenue des Champs, 75008 Paris")
                .latitude(48.8720)
                .longitude(2.2980)
                .weight(8.0)
                .volume(0.2)
                .timeSlot("14:00-16:00")
                .status(DeliveryStatus.PENDING)
                .build();

        Delivery delivery3 = Delivery.builder()
                .address("15 Boulevard Saint-Germain, 75005 Paris")
                .latitude(48.8522)
                .longitude(2.3376)
                .weight(3.0)
                .volume(0.05)
                .timeSlot("11:00-13:00")
                .status(DeliveryStatus.PENDING)
                .build();

        Delivery delivery4 = Delivery.builder()
                .address("5 Rue de Rivoli, 75004 Paris")
                .latitude(48.8558)
                .longitude(2.3582)
                .weight(6.0)
                .volume(0.15)
                .timeSlot("10:00-12:00")
                .status(DeliveryStatus.PENDING)
                .build();

        Delivery delivery5 = Delivery.builder()
                .address("30 Avenue de l'Opéra, 75002 Paris")
                .latitude(48.8668)
                .longitude(2.3336)
                .weight(4.0)
                .volume(0.08)
                .timeSlot("15:00-17:00")
                .status(DeliveryStatus.PENDING)
                .build();

        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);
        deliveryRepository.save(delivery3);
        deliveryRepository.save(delivery4);
        deliveryRepository.save(delivery5);

        System.out.println("✅ Données de test créées avec succès!");
        System.out.println("🚗 Véhicule ID: " + savedVehicle.getId());
        System.out.println("🏭 Entrepôt ID: " + savedWarehouse.getId());
        System.out.println("📦 Livraisons créées: 5");
    }
}