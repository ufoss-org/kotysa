/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalMysqlTest : AbstractJdbcMysqlTest<SelectBigDecimalRepositoryMysqlSelect>(),
    SelectBigDecimalTest<MysqlBigDecimals, SelectBigDecimalRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalRepositoryMysqlSelect(sqlClient)
}

class SelectBigDecimalRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)
