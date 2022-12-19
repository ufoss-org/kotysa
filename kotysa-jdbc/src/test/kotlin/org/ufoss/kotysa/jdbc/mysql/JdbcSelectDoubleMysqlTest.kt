/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoubleMysqlTest : AbstractJdbcMysqlTest<SelectDoubleRepositoryMysqlSelect>(),
    SelectDoubleTest<MysqlDoubles, SelectDoubleRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectDoubleRepositoryMysqlSelect(sqlClient)
}

class SelectDoubleRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)
