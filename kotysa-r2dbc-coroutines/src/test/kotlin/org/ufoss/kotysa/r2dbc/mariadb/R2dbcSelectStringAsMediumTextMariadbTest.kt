/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbMediumTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextTest


class R2dbcSelectStringAsMediumTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectStringAsMediumText>(),
    CoroutinesSelectStringAsMediumTextTest<MariadbMediumTexts, UserRepositoryJdbcMariadbSelectStringAsMediumText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsMediumText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsMediumText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsMediumTextRepository<MariadbMediumTexts>(sqlClient, MariadbMediumTexts)
