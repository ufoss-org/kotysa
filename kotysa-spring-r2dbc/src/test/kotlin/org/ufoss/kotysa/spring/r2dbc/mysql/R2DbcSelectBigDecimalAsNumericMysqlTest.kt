/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMysqlTest : AbstractR2dbcMysqlTest<BigDecimalAsNumericMysqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericMysqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericMysqlRepository>(resource)
    }

    override val repository: BigDecimalAsNumericMysqlRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericMysqlRepository(client: DatabaseClient)
    : ReactorSelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(client.sqlClient(mysqlTables), MysqlBigDecimalAsNumerics)
