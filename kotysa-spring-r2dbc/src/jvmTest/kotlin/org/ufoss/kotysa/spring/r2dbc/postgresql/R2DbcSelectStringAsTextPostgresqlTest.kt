/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextTest

class R2DbcSelectStringAsTextPostgresqlTest :
    AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlSelectStringAsText>(),
    ReactorSelectStringAsTextTest<PostgresqlTexts, UserRepositoryR2dbcPostgresqlSelectStringAsText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        UserRepositoryR2dbcPostgresqlSelectStringAsText(sqlClient)
}

class UserRepositoryR2dbcPostgresqlSelectStringAsText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsTextRepository<PostgresqlTexts>(sqlClient, PostgresqlTexts)
