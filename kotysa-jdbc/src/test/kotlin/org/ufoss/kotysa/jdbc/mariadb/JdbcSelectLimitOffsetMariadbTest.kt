/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetMariadbTest : AbstractJdbcMariadbTest<LimitOffsetRepositoryMariadbSelect>(),
    SelectLimitOffsetTest<MariadbCustomers, LimitOffsetRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryMariadbSelect(sqlClient)
}

class LimitOffsetRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
