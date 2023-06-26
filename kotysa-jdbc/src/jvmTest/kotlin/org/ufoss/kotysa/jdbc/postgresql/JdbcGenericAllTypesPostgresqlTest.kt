/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesTest

class JdbcGenericAllTypesPostgresqlTest : AbstractJdbcPostgresqlTest<GenericAllTypesRepository>(),
    GenericAllTypesTest<JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) = GenericAllTypesRepository(sqlClient)
}
