package com.capgemini.model.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {

    private String customerName;
    private BigDecimal totalRevenue;
}