/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesTest

class SpringJdbcGenericAllTypesMssqlTest : AbstractSpringJdbcMssqlTest<GenericAllTypesRepository>(),
    GenericAllTypesTest<SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        GenericAllTypesRepository(jdbcOperations.sqlClient(mssqlTables))
}
