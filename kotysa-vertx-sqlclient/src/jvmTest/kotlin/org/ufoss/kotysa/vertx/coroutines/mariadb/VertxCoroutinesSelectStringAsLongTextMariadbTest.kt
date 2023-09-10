/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbLongTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextTest


class VertxCoroutinesSelectStringAsLongTextMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectStringAsLongText>(),
    CoroutinesSelectStringAsLongTextTest<MariadbLongTexts, UserRepositoryJdbcMariadbSelectStringAsLongText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsLongText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsLongText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsLongTextRepository<MariadbLongTexts>(sqlClient, MariadbLongTexts)
