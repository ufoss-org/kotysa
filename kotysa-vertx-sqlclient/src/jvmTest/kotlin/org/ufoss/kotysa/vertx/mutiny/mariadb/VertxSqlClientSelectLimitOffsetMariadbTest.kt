/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectLimitOffsetMariadbTest :
    AbstractVertxSqlClientMariadbTest<LimitOffsetRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = LimitOffsetRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset().await().indefinitely())
            .hasSize(1)
            .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit().await().indefinitely())
            .hasSize(1)
            .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset().await().indefinitely())
            .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset().await().indefinitely())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) :
    AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom MariadbCustomers
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.id
                limit 2 offset 1
                ).fetchAll()
}
