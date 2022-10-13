/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SpringJdbcSelectKotlinxLocalDateTimeMysqlTest :
    AbstractSpringJdbcMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>(),
    SelectKotlinxLocalDateTimeTest<MysqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMysqlSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryMysqlSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateTimeRepositoryMysqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeRepository<MysqlKotlinxLocalDateTimes>(
        client.sqlClient(mysqlTables),
        MysqlKotlinxLocalDateTimes
    )
