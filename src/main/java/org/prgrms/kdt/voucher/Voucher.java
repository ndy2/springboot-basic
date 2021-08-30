package org.prgrms.kdt.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:28 오후
 */
public class Voucher {

    private final UUID voucherId;

    private final Long discount;

    private final VoucherType voucherType;

    private final LocalDateTime createdAt;

    public Voucher(UUID voucherId, Long discount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public static Voucher of(Long discount, VoucherType voucherType) {
        return new Voucher(UUID.randomUUID(), discount, voucherType, LocalDateTime.now());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}