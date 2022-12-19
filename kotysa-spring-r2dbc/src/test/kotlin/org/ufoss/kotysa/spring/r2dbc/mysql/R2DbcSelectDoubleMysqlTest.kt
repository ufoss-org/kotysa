/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMysqlTest : AbstractR2dbcMysqlTest<DoubleMysqlRepository>(),
    ReactorSelectDoubleTest<MysqlDoubles, DoubleMysqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleMysqlRepository>(resource)
    }

    override val repository: DoubleMysqlRepository by lazy {
        getContextRepository()
    }
}

class DoubleMysqlRepository(client: DatabaseClient)
    : ReactorSelectDoubleRepository<MysqlDoubles>(client.sqlClient(mysqlTables), MysqlDoubles)
