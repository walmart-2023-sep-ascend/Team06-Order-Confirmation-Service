package com.capstone.OrderService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.capstone.OrderService.model.Order;

public interface OrderRepository extends MongoRepository<Order, Integer> {
	// Query to find by order id
	@Query("{orderId:?0}")
	Optional<Order> findByOrderId(Integer orderId);
}
