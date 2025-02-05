package org.prgrms.deukyun.voucherapp.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고객 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllBlocked() {
        return customerRepository.findAllBlocked();
    }

    public void insert(Customer customer) {
        customerRepository.insert(customer);
    }
}
