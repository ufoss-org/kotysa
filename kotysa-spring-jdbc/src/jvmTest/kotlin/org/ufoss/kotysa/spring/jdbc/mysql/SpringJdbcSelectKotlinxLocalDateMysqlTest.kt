/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDates
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateMysqlTest : AbstractSpringJdbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>(),
    SelectKotlinxLocalDateTest<MysqlKotlinxLocalDates, KotlinxLocalDateRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateRepositoryMysqlSelect(jdbcOperations)
}

class KotlinxLocalDateRepositoryMysqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<MysqlKotlinxLocalDates>(client.sqlClient(mysqlTables), MysqlKotlinxLocalDates)
