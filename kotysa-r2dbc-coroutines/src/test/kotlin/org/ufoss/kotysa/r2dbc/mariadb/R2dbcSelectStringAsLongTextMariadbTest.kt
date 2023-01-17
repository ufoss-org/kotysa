/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbLongTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextTest


class R2dbcSelectStringAsLongTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectStringAsLongText>(),
    CoroutinesSelectStringAsLongTextTest<MariadbLongTexts, UserRepositoryJdbcMariadbSelectStringAsLongText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsLongText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsLongText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsLongTextRepository<MariadbLongTexts>(sqlClient, MariadbLongTexts)
