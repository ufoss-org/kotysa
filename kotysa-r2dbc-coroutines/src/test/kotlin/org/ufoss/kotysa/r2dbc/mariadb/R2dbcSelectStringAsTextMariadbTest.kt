/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextTest


class R2dbcSelectStringAsTextMariadbTest :
    AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectStringAsText>(),
    CoroutinesSelectStringAsTextTest<MariadbTexts, UserRepositoryJdbcMariadbSelectStringAsText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsTextRepository<MariadbTexts>(sqlClient, MariadbTexts)
