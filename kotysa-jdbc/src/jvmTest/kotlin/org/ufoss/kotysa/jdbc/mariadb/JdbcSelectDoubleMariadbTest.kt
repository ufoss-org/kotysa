/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoubleMariadbTest : AbstractJdbcMariadbTest<SelectDoubleRepositoryMariadbSelect>(),
    SelectDoubleTest<MariadbDoubles, SelectDoubleRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectDoubleRepositoryMariadbSelect(sqlClient)
}

class SelectDoubleRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)
