package com.capstone.OrderService.service;

import java.util.List;

import com.capstone.OrderService.exception.OrderAlreadyExistsException;
import com.capstone.OrderService.exception.OrderNotFoundException;
import com.capstone.OrderService.model.Order;

public interface OrderService {
	public List<Order> getAllOrders();
	public Order getOrderbyId(int orderid) throws OrderNotFoundException;
	public Order addOrder(Order order)throws OrderAlreadyExistsException;
	public Order getOrderbycartId(int cartid)throws OrderNotFoundException;
	public List<Order> getOrderbyuserId(int userid)throws OrderNotFoundException;
}
