/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMysqlTest : AbstractR2dbcMysqlTest<BigDecimalMysqlRepository>(),
    ReactorSelectBigDecimalTest<MysqlBigDecimals, BigDecimalMysqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalMysqlRepository>(resource)
    }

    override val repository: BigDecimalMysqlRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalMysqlRepository(client: DatabaseClient)
    : ReactorSelectBigDecimalRepository<MysqlBigDecimals>(client.sqlClient(mysqlTables), MysqlBigDecimals)
