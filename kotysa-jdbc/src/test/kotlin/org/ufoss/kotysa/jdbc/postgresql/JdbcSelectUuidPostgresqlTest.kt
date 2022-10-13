/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class JdbcSelectUuidPostgresqlTest : AbstractJdbcPostgresqlTest<UuidRepositoryPostgresqlSelect>(),
    SelectUuidTest<PostgresqlUuids, UuidRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UuidRepositoryPostgresqlSelect(sqlClient)
}

class UuidRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectUuidRepository<PostgresqlUuids>(sqlClient, PostgresqlUuids)
