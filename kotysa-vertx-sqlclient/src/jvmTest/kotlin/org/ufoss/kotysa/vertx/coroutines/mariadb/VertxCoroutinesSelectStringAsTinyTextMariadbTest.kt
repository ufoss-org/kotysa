/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbTinyTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextTest


class VertxCoroutinesSelectStringAsTinyTextMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectStringAsTinyText>(),
    CoroutinesSelectStringAsTinyTextTest<MariadbTinyTexts, UserRepositoryJdbcMariadbSelectStringAsTinyText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsTinyText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsTinyText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsTinyTextRepository<MariadbTinyTexts>(sqlClient, MariadbTinyTexts)
