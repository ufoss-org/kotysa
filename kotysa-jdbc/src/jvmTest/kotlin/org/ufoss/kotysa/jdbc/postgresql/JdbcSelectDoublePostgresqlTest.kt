/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoublePostgresqlTest : AbstractJdbcPostgresqlTest<SelectDoubleRepositoryPostgresqlSelect>(),
    SelectDoubleTest<PostgresqlDoubles, SelectDoubleRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectDoubleRepositoryPostgresqlSelect(sqlClient)
}

class SelectDoubleRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)
