/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbTinyTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTinyTextTest

class R2DbcSelectStringAsTinyTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSelectStringAsTinyText>(),
    ReactorSelectStringAsTinyTextTest<MariadbTinyTexts, UserRepositoryR2dbcMariadbSelectStringAsTinyText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbSelectStringAsTinyText(sqlClient)
}

class UserRepositoryR2dbcMariadbSelectStringAsTinyText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsTinyTextRepository<MariadbTinyTexts>(sqlClient, MariadbTinyTexts)
