/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimeMysqlTest : AbstractSpringJdbcMysqlTest<KotlinxLocalTimeRepositoryMysqlSelect>(),
    SelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalTimeRepositoryMysqlSelect(jdbcOperations)
}

class KotlinxLocalTimeRepositoryMysqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(client.sqlClient(mysqlTables), MysqlKotlinxLocalTimes)
