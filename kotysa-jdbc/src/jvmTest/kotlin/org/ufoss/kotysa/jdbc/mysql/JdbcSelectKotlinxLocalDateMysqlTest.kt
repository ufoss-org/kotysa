/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDateMysqlTest : AbstractJdbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>(),
    SelectKotlinxLocalDateTest<MysqlKotlinxLocalDates, KotlinxLocalDateRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateRepository<MysqlKotlinxLocalDates>(sqlClient, MysqlKotlinxLocalDates)
