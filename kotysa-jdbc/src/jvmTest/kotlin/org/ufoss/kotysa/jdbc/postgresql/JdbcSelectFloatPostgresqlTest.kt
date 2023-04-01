/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatPostgresqlTest : AbstractJdbcPostgresqlTest<SelectFloatRepositoryPostgresqlSelect>(),
    SelectFloatTest<PostgresqlFloats, SelectFloatRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectFloatRepositoryPostgresqlSelect(sqlClient)
}

class SelectFloatRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)
