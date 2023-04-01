/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDateMariadbTest : AbstractJdbcMariadbTest<KotlinxLocalDateRepositoryMariadbSelect>(),
    SelectKotlinxLocalDateTest<MariadbKotlinxLocalDates, KotlinxLocalDateRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateRepository<MariadbKotlinxLocalDates>(sqlClient, MariadbKotlinxLocalDates)
