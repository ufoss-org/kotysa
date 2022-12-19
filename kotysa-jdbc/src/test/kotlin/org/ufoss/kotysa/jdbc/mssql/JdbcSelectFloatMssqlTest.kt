/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatMssqlTest : AbstractJdbcMssqlTest<SelectFloatRepositoryMssqlSelect>(),
    SelectFloatTest<MssqlFloats, SelectFloatRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectFloatRepositoryMssqlSelect(sqlClient)
}

class SelectFloatRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
