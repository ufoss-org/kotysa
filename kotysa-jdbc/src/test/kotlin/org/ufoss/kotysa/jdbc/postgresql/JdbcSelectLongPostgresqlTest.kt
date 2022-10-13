/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class JdbcSelectLongPostgresqlTest : AbstractJdbcPostgresqlTest<SelectLongRepositoryPostgresqlSelect>(),
    SelectLongTest<PostgresqlLongs, SelectLongRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectLongRepositoryPostgresqlSelect(sqlClient)
}

class SelectLongRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectLongRepository<PostgresqlLongs>(sqlClient, PostgresqlLongs)
