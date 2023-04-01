/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoubleMssqlTest : AbstractJdbcMssqlTest<SelectDoubleRepositoryMssqlSelect>(),
    SelectDoubleTest<MssqlDoubles, SelectDoubleRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectDoubleRepositoryMssqlSelect(sqlClient)
}

class SelectDoubleRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
