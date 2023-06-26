/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesTest

class JdbcGenericAllTypesMssqlTest : AbstractJdbcMssqlTest<GenericAllTypesRepository>(),
    GenericAllTypesTest<JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GenericAllTypesRepository(sqlClient)
}
