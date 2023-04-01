/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class JdbcSelectKotlinxLocalTimeMariadbTest : AbstractJdbcMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    SelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(sqlClient, MariadbKotlinxLocalTimes)
