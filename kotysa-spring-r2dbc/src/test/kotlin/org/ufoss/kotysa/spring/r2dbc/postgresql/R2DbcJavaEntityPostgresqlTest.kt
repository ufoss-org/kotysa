/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityPostgresqlTest : AbstractR2dbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    ReactorJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorJavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
