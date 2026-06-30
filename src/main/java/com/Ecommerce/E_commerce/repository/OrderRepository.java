package com.Ecommerce.E_commerce.repository;

import com.Ecommerce.E_commerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Order, Long> {
}
