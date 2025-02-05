package org.prgrms.deukyun.voucherapp.domain.customer.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 고객 리포지토리
 */
public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    List<Customer> findAllBlocked();

    void clear();
}
