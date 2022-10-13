/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class JdbcSelectLocalDateTimeMariadbTest : AbstractJdbcMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    SelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class LocalDateTimeRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeRepository<MariadbLocalDateTimes>(sqlClient, MariadbLocalDateTimes)
