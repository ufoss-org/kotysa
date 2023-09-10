/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextTest


class VertxCoroutinesSelectStringAsTextMysqlTest :
    AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectStringAsText>(),
    CoroutinesSelectStringAsTextTest<MysqlTexts, UserRepositoryJdbcMysqlSelectStringAsText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsTextRepository<MysqlTexts>(sqlClient, MysqlTexts)
