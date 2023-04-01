/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDateMariadbTest : AbstractJdbcMariadbTest<LocalDateRepositoryMariadbSelect>(),
    SelectLocalDateTest<MariadbLocalDates, LocalDateRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateRepositoryMariadbSelect(sqlClient)
}

class LocalDateRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<MariadbLocalDates>(sqlClient, MariadbLocalDates)
