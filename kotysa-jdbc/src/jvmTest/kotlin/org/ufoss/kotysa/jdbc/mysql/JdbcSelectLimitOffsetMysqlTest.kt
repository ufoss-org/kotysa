/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetMysqlTest : AbstractJdbcMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    SelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryMysqlSelect(sqlClient)
}

class LimitOffsetRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
