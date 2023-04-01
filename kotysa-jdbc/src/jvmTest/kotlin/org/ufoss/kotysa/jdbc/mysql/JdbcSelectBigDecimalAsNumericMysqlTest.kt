/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class JdbcSelectBigDecimalAsNumericMysqlTest : AbstractJdbcMysqlTest<SelectBigDecimalAsNumericRepositoryMysqlSelect>(),
    SelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, SelectBigDecimalAsNumericRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalAsNumericRepositoryMysqlSelect(sqlClient)
}

class SelectBigDecimalAsNumericRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)
