/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextTest


class VertxCoroutinesSelectStringAsTextMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectStringAsText>(),
    CoroutinesSelectStringAsTextTest<MariadbTexts, UserRepositoryJdbcMariadbSelectStringAsText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectStringAsText(sqlClient)
}

class UserRepositoryJdbcMariadbSelectStringAsText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsTextRepository<MariadbTexts>(sqlClient, MariadbTexts)
