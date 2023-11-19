package com.capstone.OrderService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.OrderService.exception.OrderAlreadyExistsException;
import com.capstone.OrderService.model.Order;
import com.capstone.OrderService.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository repo;
	
	public List<Order> getAllOrders()
	{
		return repo.findAll();
	}
	public Order getOrderbyId(int oid) {
		Order o=null;
		System.out.println(oid);	
		Optional<Order> opt=this.repo.findByOrderId(oid);
		if(opt.isPresent()) {
			o=opt.get();
			System.out.println(o);			
		}
		else
		
			System.out.println("Order not found");
		return o;	
	}
	public Order addOrder(Order ord)throws OrderAlreadyExistsException
	{
		Optional<Order> opt=this.repo.findById((int) ord.getOrderId());
		if(opt.isPresent()) {
			throw new OrderAlreadyExistsException();
		}
		repo.save(ord);
		return ord;
	}
}
