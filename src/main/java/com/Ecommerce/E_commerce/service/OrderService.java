package com.Ecommerce.E_commerce.service;

import com.Ecommerce.E_commerce.entity.Order;
import com.Ecommerce.E_commerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void save(Order order) {
        repository.save(order);
    }
}
