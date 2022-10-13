/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimeMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalTimeMysqlRepository>(),
    ReactorSelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeMysqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalTimeMysqlRepository>(resource)
    }

    override val repository: KotlinxLocalTimeMysqlRepository by lazy {
        getContextRepository()
    }
}

class KotlinxLocalTimeMysqlRepository(client: DatabaseClient) :
    ReactorSelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(
        client.sqlClient(mysqlTables),
        MysqlKotlinxLocalTimes
    )
