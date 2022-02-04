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
            (sqlClient selectMin MysqlCustomers.age
                    from MysqlCustomers
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MysqlCustomers.age
                    from MysqlCustomers
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MysqlCustomers.age
                    from MysqlCustomers
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MysqlCustomers.age
                    from MysqlCustomers
                    ).fetchOne()
}
