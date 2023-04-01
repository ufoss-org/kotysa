/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class JdbcSelectLocalTimeMariadbTest : AbstractJdbcMariadbTest<LocalTimeRepositoryMariadbSelect>(),
    SelectLocalTimeTest<MariadbLocalTimes, LocalTimeRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalTimeRepositoryMariadbSelect(sqlClient)
}

class LocalTimeRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectLocalTimeRepository<MariadbLocalTimes>(sqlClient, MariadbLocalTimes)
