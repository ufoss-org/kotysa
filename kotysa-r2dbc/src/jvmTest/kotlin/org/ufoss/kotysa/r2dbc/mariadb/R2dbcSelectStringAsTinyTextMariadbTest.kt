/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbTinyTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextTest


class R2dbcSelectStringAsTinyTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectStringAsTinyText>(),
    CoroutinesSelectStringAsTinyTextTest<MariadbTinyTexts, UserRepositoryJdbcMariadbSelectStringAsTinyText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsTinyText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsTinyText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsTinyTextRepository<MariadbTinyTexts>(sqlClient, MariadbTinyTexts)
