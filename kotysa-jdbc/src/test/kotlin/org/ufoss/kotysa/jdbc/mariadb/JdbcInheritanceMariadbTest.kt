/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritanceMariadbTest : AbstractJdbcMariadbTest<InheritanceMariadbRepository>(),
    InheritanceTest<MariadbInheriteds, InheritanceMariadbRepository, JdbcTransaction> {
    override val table = MariadbInheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceMariadbRepository(sqlClient)
}

class InheritanceMariadbRepository(sqlClient: JdbcSqlClient) :
    InheritanceRepository<MariadbInheriteds>(sqlClient, MariadbInheriteds)
