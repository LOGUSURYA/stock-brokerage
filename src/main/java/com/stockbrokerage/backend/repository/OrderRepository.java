package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.Order;
import com.stockbrokerage.backend.entity.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockbrokerage.backend.enums.OrderStatus;
import com.stockbrokerage.backend.enums.OrderType;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByClient(ClientAccount client);
    Long countByOrderType(OrderType orderType);

   @Query("SELECT COUNT(o) FROM Order o")
   Long getTotalOrders();

   List<Order> findAllByOrderByOrderDateDesc(Pageable pageable);
   List<Order> findByOrderType(OrderType orderType);
   List<Order> findByStatus(OrderStatus status);

List<Order> findByOrderTypeAndStatus(
        OrderType orderType,
        OrderStatus status
);

}