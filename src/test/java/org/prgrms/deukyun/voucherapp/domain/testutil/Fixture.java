package org.prgrms.deukyun.voucherapp.domain.testutil;

import org.apache.commons.lang3.RandomUtils;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.Collections;

import static java.util.Collections.*;
import static org.apache.commons.lang3.RandomUtils.*;

public class Fixture {

    public static FixedAmountDiscountVoucher fixedAmountDiscountVoucher() {
        return new FixedAmountDiscountVoucher(2000L);
    }

    public static PercentDiscountVoucher percentDiscountVoucher() {
        return new PercentDiscountVoucher(20L);
    }

    public static Voucher voucher() {
        return new FixedAmountDiscountVoucher(2000L);
    }

    public static Customer customer() {
        return new Customer("ndy", nextBoolean(), emptyList());
    }
}
