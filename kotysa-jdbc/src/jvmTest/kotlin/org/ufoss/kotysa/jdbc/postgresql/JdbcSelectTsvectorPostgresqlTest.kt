/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.repositories.blocking.SelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTsvectorTest

class JdbcSelectTsvectorPostgresqlTest : AbstractJdbcPostgresqlTest<TsvectorRepositoryPostgresqlSelect>(),
    SelectTsvectorTest<TsvectorRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        TsvectorRepositoryPostgresqlSelect(sqlClient)
}

class TsvectorRepositoryPostgresqlSelect(sqlClient: PostgresqlJdbcSqlClient) : SelectTsvectorRepository(sqlClient)
