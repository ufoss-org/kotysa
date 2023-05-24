/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class JdbcSelectIntAsIdentitiesPostgresqlTest :
    AbstractJdbcPostgresqlTest<SelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    SelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, SelectIntAsIdentitiesRepositoryPostgresqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
