/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.blocking.GenericAllTypesTest

class JdbcGenericAllTypesH2Test : AbstractJdbcH2Test<GenericAllTypesRepository>(),
    GenericAllTypesTest<JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GenericAllTypesRepository(sqlClient)
}
