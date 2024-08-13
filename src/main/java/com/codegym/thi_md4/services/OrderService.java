package com.codegym.thi_md4.services;

import com.codegym.thi_md4.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        // Default handling for null dates
        if (startDate == null) {
            startDate = LocalDateTime.MIN;  // The earliest possible date
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();  // Current date and time
        }
        return orderRepository.findByPurchaseDateBetween(startDate, endDate);
    }

    public List<Order> getTopOrders(int limit) {
        return orderRepository.findTopOrders(limit);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
