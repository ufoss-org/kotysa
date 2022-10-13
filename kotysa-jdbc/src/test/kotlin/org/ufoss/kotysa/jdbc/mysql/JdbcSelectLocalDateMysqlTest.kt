/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDateMysqlTest : AbstractJdbcMysqlTest<LocalDateRepositoryMysqlSelect>(),
    SelectLocalDateTest<MysqlLocalDates, LocalDateRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateRepositoryMysqlSelect(sqlClient)
}

class LocalDateRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<MysqlLocalDates>(sqlClient, MysqlLocalDates)
