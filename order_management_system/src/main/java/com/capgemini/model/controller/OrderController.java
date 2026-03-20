package com.capgemini.model.controller;

import com.capgemini.model.entity.Order;
import com.capgemini.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public Map<String, Object> createOrder(@RequestBody Order order) {
		return orderService.createOrder(order);
	}

	@GetMapping
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable int id) {
		return orderService.getOrderById(id);
	}

	@PutMapping("/{id}")
	public Map<String, String> updateOrder(@PathVariable int id, @RequestBody Order order) {
		return orderService.updateOrder(id, order);
	}

	@DeleteMapping("/{id}")
	public Map<String, String> cancelOrder(@PathVariable int id) {
		return orderService.cancelOrder(id);
	}
}