package com.dto.delivery_tour_optimizer.config;

import com.dto.delivery_tour_optimizer.model.Customer;
import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import com.dto.delivery_tour_optimizer.repository.CustomerRepository;
import com.dto.delivery_tour_optimizer.repository.DeliveryRepository;
import com.dto.delivery_tour_optimizer.repository.VehicleRepository;
import com.dto.delivery_tour_optimizer.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Profile("!dev")
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public DataInitializer(VehicleRepository vehicleRepository,
                           WarehouseRepository warehouseRepository,
                           DeliveryRepository deliveryRepository,
                           CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
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

        // Créer des clients
        Customer customer1 = Customer.builder()
                .name("Alice Dupont")
                .address("10 Rue de Paris, 75001 Paris")
                .latitude(48.8584)
                .longitude(2.2945)
                .preferredTimeSlot("09:00-11:00")
                .build();

        Customer customer2 = Customer.builder()
                .name("Bob Martin")
                .address("25 Avenue des Champs, 75008 Paris")
                .latitude(48.8720)
                .longitude(2.2980)
                .preferredTimeSlot("14:00-16:00")
                .build();

        Customer customer3 = Customer.builder()
                .name("Claire Bernard")
                .address("15 Boulevard Saint-Germain, 75005 Paris")
                .latitude(48.8522)
                .longitude(2.3376)
                .preferredTimeSlot("11:00-13:00")
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        // Créer des livraisons de test
        Delivery delivery1 = Delivery.builder()
                .weight(5.0)
                .volume(0.1)
                .timeSlot(customer1.getPreferredTimeSlot())
                .plannedTime(LocalTime.of(9, 0))
                .actualTime(LocalTime.of(9, 15))
                .status(DeliveryStatus.PENDING)
                .customer(customer1)
                .build();

        Delivery delivery2 = Delivery.builder()
                .weight(8.0)
                .volume(0.2)
                .timeSlot(customer2.getPreferredTimeSlot())
                .plannedTime(LocalTime.of(14, 0))
                .actualTime(LocalTime.of(13, 50))
                .status(DeliveryStatus.PENDING)
                .customer(customer2)
                .build();

        Delivery delivery3 = Delivery.builder()
                .weight(3.0)
                .volume(0.05)
                .timeSlot(customer3.getPreferredTimeSlot())
                .plannedTime(LocalTime.of(11, 0))
                .actualTime(LocalTime.of(11, 30))
                .status(DeliveryStatus.PENDING)
                .customer(customer3)
                .build();

        Delivery delivery4 = Delivery.builder()
                .weight(6.0)
                .volume(0.15)
                .timeSlot("10:00-12:00")
                .plannedTime(LocalTime.of(10, 30))
                .actualTime(LocalTime.of(10, 45))
                .status(DeliveryStatus.PENDING)
                .customer(customer1)
                .build();

        Delivery delivery5 = Delivery.builder()
                .weight(4.0)
                .volume(0.08)
                .timeSlot("15:00-17:00")
                .plannedTime(LocalTime.of(15, 0))
                .actualTime(LocalTime.of(15, 10))
                .status(DeliveryStatus.PENDING)
                .customer(customer2)
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