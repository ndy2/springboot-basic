package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 메모리 바우처 리포지토리
 */
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage;

    public MemoryVoucherRepository() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher insert(Voucher elem) {
        storage.put(elem.getId(), elem);
        return elem;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return new ArrayList<>(storage.values()).stream()
                .filter(v -> v.getCustomerId().isPresent()
                        && v.getCustomerId().get().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
