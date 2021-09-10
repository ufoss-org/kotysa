/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectMinMaxAvgSumH2Test : AbstractSpringJdbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryPostgresqlSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryPostgresqlSelect by lazy {
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

class MinMaxAvgSumRepositoryPostgresqlSelect(client: JdbcOperations) :
        AbstractCustomerRepositorySpringJdbcPostgresql(client) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum POSTGRESQL_CUSTOMER.age
                    from POSTGRESQL_CUSTOMER
                    ).fetchOne()
}
