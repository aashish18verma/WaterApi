package com.rubicon.WaterApi.repository;

import com.rubicon.WaterApi.model.WaterOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface WaterOrderRepository extends JpaRepository<WaterOrder,Integer> {
    List<WaterOrder> findAllOrderByfarmId(Integer farmId);
    WaterOrder findOrderByorderId(Integer orderId);
}
