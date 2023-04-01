/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalMssqlTest : AbstractJdbcMssqlTest<SelectBigDecimalRepositoryMssqlSelect>(),
    SelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalRepositoryMssqlSelect(sqlClient)
}

class SelectBigDecimalRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
