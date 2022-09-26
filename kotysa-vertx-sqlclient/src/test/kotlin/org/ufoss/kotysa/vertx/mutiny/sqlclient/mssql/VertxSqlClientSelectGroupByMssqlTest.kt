/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectGroupByMssqlTest :
    AbstractVertxSqlClientMssqlTest<GroupByRepositoryMssqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = GroupByRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().await().indefinitely())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMssqlSelect(sqlClient: VertxSqlClient) : AbstractCustomerRepositoryMssql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MssqlCustomers.id and MssqlCustomers.country
                from MssqlCustomers
                groupBy MssqlCustomers.country
                ).fetchAll()
}
