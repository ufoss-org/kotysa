/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class JdbcSelectIntPostgresqlTest : AbstractJdbcPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    SelectIntTest<PostgresqlInts, SelectIntRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectIntRepositoryPostgresqlSelect(sqlClient)
}


class SelectIntRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) : SelectIntRepository<PostgresqlInts>(sqlClient, PostgresqlInts)
