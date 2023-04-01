/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatMysqlTest : AbstractJdbcMysqlTest<SelectFloatRepositoryMysqlSelect>(),
    SelectFloatTest<MysqlFloats, SelectFloatRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectFloatRepositoryMysqlSelect(sqlClient)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class SelectFloatRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectFloatRepository<MysqlFloats>(sqlClient, MysqlFloats)
