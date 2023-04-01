/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatMariadbTest : AbstractJdbcMariadbTest<SelectFloatRepositoryMariadbSelect>(),
    SelectFloatTest<MariadbFloats, SelectFloatRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectFloatRepositoryMariadbSelect(sqlClient)
    
    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class SelectFloatRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectFloatRepository<MariadbFloats>(sqlClient, MariadbFloats)
