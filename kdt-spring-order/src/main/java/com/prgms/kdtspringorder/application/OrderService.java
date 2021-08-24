package com.prgms.kdtspringorder.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgms.kdtspringorder.domain.model.order.Order;
import com.prgms.kdtspringorder.domain.model.order.OrderItem;
import com.prgms.kdtspringorder.domain.model.order.OrderRepository;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final VoucherService voucherService;

    public OrderService(OrderRepository orderRepository, VoucherService voucherService) {
        this.orderRepository = orderRepository;
        this.voucherService = voucherService;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), customerId, orderItems);
        orderRepository.save(order);
        return order;
    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.save(order);
        voucherService.useVoucher(voucherId);
        return order;
    }
}