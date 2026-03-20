package com.capgemini.model.controller;

import com.capgemini.model.dto.RevenueDTO;
import com.capgemini.model.entity.Customer;
import com.capgemini.model.service.CustomerService;
import com.capgemini.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;
	private final OrderService orderService;

	@PostMapping
	public Customer create(@RequestBody Customer customer) {
		return service.create(customer);
	}

	@GetMapping
	public List<Customer> getAll() {
		return service.getAll();
	}

	@GetMapping("/{customerId}")
	public Customer get(@PathVariable Long customerId) {
		return service.get(customerId);
	}

	@PutMapping("/{customerId}")
	public Customer update(@PathVariable Long customerId, @RequestBody Customer customer) {
		return service.update(customerId, customer);
	}

	@DeleteMapping("/{customerId}")
	public void delete(@PathVariable Long customerId) {
		service.delete(customerId);
	}

	@GetMapping("/{customerId}/restore")
	public void restore(@PathVariable Long customerId) {
		service.restore(customerId);
	}

	@GetMapping("/revenue-summary")
	public List<RevenueDTO> revenue() {
		return orderService.revenue();
	}
}