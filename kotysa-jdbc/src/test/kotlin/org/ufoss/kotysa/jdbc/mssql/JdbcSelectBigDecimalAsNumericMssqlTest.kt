/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class JdbcSelectBigDecimalAsNumericMssqlTest : AbstractJdbcMssqlTest<SelectBigDecimalAsNumericRepositoryMssqlSelect>(),
    SelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalAsNumericRepositoryMssqlSelect(sqlClient)
}

class SelectBigDecimalAsNumericRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
