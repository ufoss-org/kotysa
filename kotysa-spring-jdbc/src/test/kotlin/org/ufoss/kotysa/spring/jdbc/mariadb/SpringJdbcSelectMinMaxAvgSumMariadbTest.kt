/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectMinMaxAvgSumMariadbTest : AbstractSpringJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMariadbSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMariadbSelect by lazy {
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

class MinMaxAvgSumRepositoryMariadbSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMariadb(client) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MARIADB_CUSTOMER.age
                    from MARIADB_CUSTOMER
                    ).fetchOne()
}
