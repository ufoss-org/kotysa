/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDatePostgresqlTest :
    AbstractJdbcPostgresqlTest<KotlinxLocalDateRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalDateTest<PostgresqlKotlinxLocalDates, KotlinxLocalDateRepositoryPostgresqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateRepository<PostgresqlKotlinxLocalDates>(sqlClient, PostgresqlKotlinxLocalDates)
