/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbMediumTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextTest


class VertxCoroutinesSelectStringAsMediumTextMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectStringAsMediumText>(),
    CoroutinesSelectStringAsMediumTextTest<MariadbMediumTexts, UserRepositoryJdbcMariadbSelectStringAsMediumText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsMediumText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsMediumText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsMediumTextRepository<MariadbMediumTexts>(sqlClient, MariadbMediumTexts)
