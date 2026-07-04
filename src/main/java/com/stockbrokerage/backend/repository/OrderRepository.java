package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.Order;
import com.stockbrokerage.backend.entity.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient(ClientAccount client);

}