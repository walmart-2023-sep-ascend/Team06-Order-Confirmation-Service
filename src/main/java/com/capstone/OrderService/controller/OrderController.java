package com.capstone.OrderService.controller;

import java.util.List;

import com.capstone.OrderService.service.SequenceGeneratorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.capstone.OrderService.exception.OrderAlreadyExistsException;
import com.capstone.OrderService.model.Order;
import com.capstone.OrderService.service.OrderService;

@RestController
public class OrderController {
	
	private static final String ORDER_SERVICE = "OrderService";
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService oserv;
	ResponseEntity<?> resentity;
	
	@Autowired
	RestTemplate resttemplate;
	
	@GetMapping("/orders")
	public ResponseEntity<?> getAllOrders(){
		List<Order> list=oserv.getAllOrders();
		resentity=new ResponseEntity<>(list,HttpStatus.OK);
		return resentity;
	}
	
	@GetMapping("/orders/{oid}")
	public ResponseEntity<?> getOrder(@PathVariable("oid") int oid){
		System.out.println("order id:"+oid);
		Order o=oserv.getOrderbyId(oid);
		resentity=new ResponseEntity<>(o,HttpStatus.OK);
		return resentity;
	}
		
	@PostMapping("/addorder")
	@ExceptionHandler(OrderAlreadyExistsException.class)
	public ResponseEntity<?> addOrd(@RequestBody Order ord)throws OrderAlreadyExistsException
	{
		try {
			resttemplate=new RestTemplate();
			ord.setOrderId(SequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
			ord.setStatusOfOrder("Placed");
			oserv.addOrder(ord);
			resentity=new ResponseEntity<>(ord,HttpStatus.CREATED);
		/*
			//Call Inventory update service 
			if(resentity.getStatusCode()==HttpStatus.CREATED) {
				int cartid=ord.getCartId();
				ResponseEntity<String> Inventorymsg=resttemplate.getForEntity("http://localhost:6003/inventoryupdate/{cartid}", String.class, cartid);
				logger.info("Inventory service:"+Inventorymsg);				
			}*/
		}
		
		catch(OrderAlreadyExistsException e)
		{
			throw new OrderAlreadyExistsException();
			}
		catch(Exception e)
		{
			logger.info("Exception: ",e);
			resentity=new ResponseEntity<>("Adding Failed,pls try later",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
}