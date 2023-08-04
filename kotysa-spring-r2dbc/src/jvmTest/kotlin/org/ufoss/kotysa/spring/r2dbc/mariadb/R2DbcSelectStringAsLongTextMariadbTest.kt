/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbLongTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsLongTextTest

class R2DbcSelectStringAsLongTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSelectStringAsLongText>(),
    ReactorSelectStringAsLongTextTest<MariadbLongTexts, UserRepositoryR2dbcMariadbSelectStringAsLongText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbSelectStringAsLongText(sqlClient)
}

class UserRepositoryR2dbcMariadbSelectStringAsLongText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsLongTextRepository<MariadbLongTexts>(sqlClient, MariadbLongTexts)
