/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectMinMaxAvgSumMysqlTest : AbstractSpringJdbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMysqlSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMysqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge())
                .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge())
                .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge())
                .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge())
                .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryMysqlSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMysql(client) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MYSQL_CUSTOMER.age
                    from MYSQL_CUSTOMER
                    ).fetchOne()
}
