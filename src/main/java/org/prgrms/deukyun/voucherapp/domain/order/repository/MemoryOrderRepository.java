package org.prgrms.deukyun.voucherapp.domain.order.repository;

import org.prgrms.deukyun.voucherapp.domain.common.repository.MemoryRepository;
import org.prgrms.deukyun.voucherapp.domain.order.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * 메모리 주문 리포지토리
 */
@Repository
public class MemoryOrderRepository extends MemoryRepository<Order> implements OrderRepository {
}
