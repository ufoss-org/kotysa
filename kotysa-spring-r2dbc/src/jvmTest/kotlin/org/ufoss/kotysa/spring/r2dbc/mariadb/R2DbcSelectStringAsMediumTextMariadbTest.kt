/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbMediumTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsMediumTextTest

class R2DbcSelectStringAsMediumTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSelectStringAsMediumText>(),
    ReactorSelectStringAsMediumTextTest<MariadbMediumTexts, UserRepositoryR2dbcMariadbSelectStringAsMediumText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbSelectStringAsMediumText(sqlClient)
}

class UserRepositoryR2dbcMariadbSelectStringAsMediumText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsMediumTextRepository<MariadbMediumTexts>(sqlClient, MariadbMediumTexts)
