package com.dto.delivery_tour_optimizer.repository;

import com.dto.delivery_tour_optimizer.model.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
}
