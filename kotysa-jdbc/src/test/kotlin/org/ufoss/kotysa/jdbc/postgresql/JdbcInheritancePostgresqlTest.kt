/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlInheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritancePostgresqlTest : AbstractJdbcPostgresqlTest<InheritancePostgresqlRepository>(),
    InheritanceTest<PostgresqlInheriteds, InheritancePostgresqlRepository, JdbcTransaction> {
    override val table = PostgresqlInheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritancePostgresqlRepository(sqlClient)
}

class InheritancePostgresqlRepository(sqlClient: JdbcSqlClient) :
    InheritanceRepository<PostgresqlInheriteds>(sqlClient, PostgresqlInheriteds)
