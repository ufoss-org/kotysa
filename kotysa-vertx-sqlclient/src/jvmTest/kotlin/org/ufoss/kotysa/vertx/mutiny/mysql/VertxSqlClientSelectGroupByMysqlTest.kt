/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectGroupByMysqlTest :
    AbstractVertxSqlClientMysqlTest<GroupByRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().await().indefinitely())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(sqlClient: MutinyVertxSqlClient) : AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MysqlCustomers.id and MysqlCustomers.country
                from MysqlCustomers
                groupBy MysqlCustomers.country
                ).fetchAll()
}
