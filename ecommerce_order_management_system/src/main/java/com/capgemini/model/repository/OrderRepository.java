package com.capgemini.model.repository;

import com.capgemini.model.dto.RevenueDTO;
import com.capgemini.model.entity.Order;
import com.capgemini.model.enums.OrderStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findByCustomerCustomerId(Long customerId, Pageable pageable);

	Page<Order> findByCustomerCustomerIdAndStatus(Long customerId, OrderStatus status, Pageable pageable);

	@Query("SELECT new com.capgemini.model.dto.RevenueDTO(o.customer.fullName, SUM(o.totalAmount)) "
			+ "FROM Order o WHERE o.status = 'DELIVERED' GROUP BY o.customer.fullName")
	List<RevenueDTO> getRevenueSummary();
}