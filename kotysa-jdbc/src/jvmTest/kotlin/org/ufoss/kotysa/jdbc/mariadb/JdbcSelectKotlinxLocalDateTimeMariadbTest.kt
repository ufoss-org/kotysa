/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class JdbcSelectKotlinxLocalDateTimeMariadbTest :
    AbstractJdbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>(),
    SelectKotlinxLocalDateTimeTest<MariadbKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMariadbSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeRepository<MariadbKotlinxLocalDateTimes>(sqlClient, MariadbKotlinxLocalDateTimes)
