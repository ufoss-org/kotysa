/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectLimitOffsetMysqlTest: AbstractJdbcMysqlTest<LimitOffsetRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset())
                .hasSize(1)
                .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit())
                .hasSize(1)
                .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset())
                .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryMysqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMysql(sqlClient) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.id
                    limit 2 offset 1
                    ).fetchAll()
}
