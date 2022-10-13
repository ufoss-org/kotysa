/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.postgresql.util.PSQLException
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class JdbcInsertPostgresqlTest : AbstractJdbcPostgresqlTest<RepositoryPostgresqlInsert>(),
    InsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, RepositoryPostgresqlInsert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryPostgresqlInsert(sqlClient)
    override val exceptionClass = PSQLException::class.java
}

class RepositoryPostgresqlInsert(sqlClient: JdbcSqlClient) :
    InsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers>(
        sqlClient,
        PostgresqlInts,
        PostgresqlLongs,
        PostgresqlCustomers
    )
