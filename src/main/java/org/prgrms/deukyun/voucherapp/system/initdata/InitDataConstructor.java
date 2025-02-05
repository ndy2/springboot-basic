package org.prgrms.deukyun.voucherapp.system.initdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 초기 데이터 생성자
 */
@Component
@RequiredArgsConstructor
public class InitDataConstructor {

    private final CustomerData customerData;
    private final VoucherData voucherData;

    @PostConstruct
    public void initData() {
        customerData.initData();
        voucherData.initData();
    }
}
