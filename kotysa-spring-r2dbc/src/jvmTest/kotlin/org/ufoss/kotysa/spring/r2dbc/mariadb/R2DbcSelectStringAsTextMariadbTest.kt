/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextTest

class R2DbcSelectStringAsTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSelectStringAsText>(),
    ReactorSelectStringAsTextTest<MariadbTexts, UserRepositoryR2dbcMariadbSelectStringAsText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbSelectStringAsText(sqlClient)
}

class UserRepositoryR2dbcMariadbSelectStringAsText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsTextRepository<MariadbTexts>(sqlClient, MariadbTexts)
