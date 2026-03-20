package com.capgemini.model.controller;

import com.capgemini.model.entity.Order;
import com.capgemini.model.enums.OrderStatus;
import com.capgemini.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@PostMapping
	public Order create(@PathVariable Long customerId, @RequestBody Order order) {
		return service.create(customerId, order);
	}

	@GetMapping
	public Page<Order> getOrders(@PathVariable Long customerId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(required = false) OrderStatus status) {
		return service.getOrders(customerId, page, size, status);
	}

	@GetMapping("/{orderId}")
	public Order get(@PathVariable Long customerId, @PathVariable Long orderId) {
		return service.get(customerId, orderId);
	}

	@PutMapping("/{orderId}")
	public Order update(@PathVariable Long customerId, @PathVariable Long orderId, @RequestBody Order order) {
		return service.update(customerId, orderId, order);
	}

	@PatchMapping("/{orderId}/cancel")
	public Order cancel(@PathVariable Long customerId, @PathVariable Long orderId) {
		return service.cancel(customerId, orderId);
	}
}