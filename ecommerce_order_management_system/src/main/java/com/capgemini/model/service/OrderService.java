package com.capgemini.model.service;

import com.capgemini.model.dto.RevenueDTO;
import com.capgemini.model.entity.*;
import com.capgemini.model.enums.OrderStatus;
import com.capgemini.model.exception.*;
import com.capgemini.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepo;
	private final CustomerRepository customerRepo;

	public Order create(Long customerId, Order order) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		if (order.getQuantity() < 1)
			throw new BadRequestException("Quantity must be >= 1");

		if (order.getPricePerUnit().compareTo(BigDecimal.valueOf(0.01)) < 0)
			throw new BadRequestException("Price must be >= 0.01");

		order.setCustomer(customer);

		order.setTotalAmount(order.getPricePerUnit().multiply(BigDecimal.valueOf(order.getQuantity())));

		return orderRepo.save(order);
	}

	public Page<Order> getOrders(Long customerId, int page, int size, OrderStatus status) {

		Pageable pageable = PageRequest.of(page, size);

		return (status != null) ? orderRepo.findByCustomerCustomerIdAndStatus(customerId, status, pageable)
				: orderRepo.findByCustomerCustomerId(customerId, pageable);
	}

	public Order get(Long customerId, Long orderId) {
		return orderRepo.findById(orderId).filter(o -> o.getCustomer().getCustomerId().equals(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}

	public Order update(Long customerId, Long orderId, Order updated) {

		Order order = get(customerId, orderId);

		order.setProductName(updated.getProductName());
		order.setQuantity(updated.getQuantity());
		order.setPricePerUnit(updated.getPricePerUnit());

		order.setTotalAmount(updated.getPricePerUnit().multiply(BigDecimal.valueOf(updated.getQuantity())));

		return orderRepo.save(order);
	}

	public Order cancel(Long customerId, Long orderId) {

		Order order = get(customerId, orderId);

		if (!(order.getStatus() == OrderStatus.PLACED || order.getStatus() == OrderStatus.CONFIRMED)) {
			throw new BadRequestException("Cannot cancel an order with status " + order.getStatus());
		}

		order.setStatus(OrderStatus.CANCELLED);
		return orderRepo.save(order);
	}

	public List<RevenueDTO> revenue() {
		return orderRepo.getRevenueSummary();
	}
}