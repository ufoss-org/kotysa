/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectLimitOffsetMysqlTest :
    AbstractVertxSqlClientMysqlTest<LimitOffsetRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = LimitOffsetRepositoryMysqlSelect(sqlClient)

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

class LimitOffsetRepositoryMysqlSelect(sqlClient: MutinyVertxSqlClient) :
    AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom MysqlCustomers
                orderByAsc MysqlCustomers.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom MysqlCustomers
                orderByAsc MysqlCustomers.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom MysqlCustomers
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom MysqlCustomers
                orderByAsc MysqlCustomers.id
                limit 2 offset 1
                ).fetchAll()
}
