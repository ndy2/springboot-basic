package org.prgrms.kdt.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.EmbeddedMysqlConnector;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:14 오후
 */
class CustomerJdbcRepositoryTest extends EmbeddedMysqlConnector {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @BeforeEach
    void beforeEach() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객 추가 테스트")
    void testInsert() {
        UUID customerId = UUID.randomUUID();
        var customer = givenCustomer(customerId);
        customerJdbcRepository.insert(customer);

        var unknown = customerJdbcRepository.findById(customer.getCustomerId());

        assertThat(unknown.isEmpty(), is(false));
        assertThat(unknown.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("중복된 이메일로 추가할 경우 예외 테스트")
    void fail_testInsert() {
        var customer = givenCustomer("tester");
        var duplicateEmailCustomer = givenCustomer("tester");
        customerJdbcRepository.insert(customer);

        assertThatThrownBy(() -> customerJdbcRepository.insert(duplicateEmailCustomer))
                .hasMessageContaining("Duplicate entry 'tester@email.com' for key 'unq_user_email'");
    }

    @Test
    @DisplayName("전체 고객 조회 테스트")
    void testFindAll() {
        customerJdbcRepository.insert(givenCustomer("tester1"));
        customerJdbcRepository.insert(givenCustomer("tester2"));
        customerJdbcRepository.insert(givenCustomer("tester3"));

        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(3));
    }

    @Test
    @DisplayName("고객 이름 조회 테스트")
    void testFindByName() {
        var customer = givenCustomer(UUID.randomUUID());
        customerJdbcRepository.insert(customer);

        var findCustomer = customerJdbcRepository.findByName(customer.getName());
        assertThat(findCustomer.isEmpty(), is(false));
    }

    @Test
    @DisplayName("고객 이름 조회 실패")
    void fail_testFindByName() {
        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객 이메일 조회 테스트")
    void testFindByEmail() {
        var customer = givenCustomer(UUID.randomUUID());
        customerJdbcRepository.insert(customer);

        var findCustomer = customerJdbcRepository.findByEmail(customer.getEmail());
        assertThat(findCustomer.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByEmail("unknown-user@email.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객 이메일 조회 실패 테스트")
    void fail_testFindByEmail() {
        var unknown = customerJdbcRepository.findByEmail("unknown-user@email.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객 수정 테스트")
    void testUpdate() {
        var customer = givenCustomer(UUID.randomUUID());
        customerJdbcRepository.insert(customer);

        customer.changeName("updated-user");
        customerJdbcRepository.update(customer);

        var all = customerJdbcRepository.findAll();

        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(customer)));

        var findCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get(), samePropertyValuesAs(customer));
    }


    private Customer givenCustomer(UUID customerId) {
        return new Customer(customerId, "tester", "tester@email.comn", LocalDateTime.now());
    }

    private Customer givenCustomer(String name) {
        return new Customer(UUID.randomUUID(), name, name + "@email.com", LocalDateTime.now());
    }
}