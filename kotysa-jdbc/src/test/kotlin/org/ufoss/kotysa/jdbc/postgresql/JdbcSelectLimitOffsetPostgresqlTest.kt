/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetPostgresqlTest : AbstractJdbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>(),
    SelectLimitOffsetTest<PostgresqlCustomers, LimitOffsetRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryPostgresqlSelect(sqlClient)
}

class LimitOffsetRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
