/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectMinMaxAvgSumMariadbTest : AbstractR2dbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMariadbSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge().block())
                .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge().block())
                .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge().block())
                .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge().block())
                .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: ReactorSqlClient) : org.ufoss.kotysa.spring.r2dbc.mariadb.AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum MariadbCustomers.age
                    from MariadbCustomers
                    ).fetchOne()
}
