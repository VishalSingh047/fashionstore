package com.fashionstore.fashionstore.dto;

import com.fashionstore.fashionstore.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDistribution {
    private OrderStatus status;
    private long count;
}
