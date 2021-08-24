package org.prgrms.kdtspringdemo.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String saveCSV();

    long discount(long beforeDiscount);
}