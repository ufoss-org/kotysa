/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextTest


class VertxCoroutinesSelectStringAsLongTextMysqlTest :
    AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectStringAsLongText>(),
    CoroutinesSelectStringAsLongTextTest<MysqlLongTexts, UserRepositoryJdbcMysqlSelectStringAsLongText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsLongText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsLongText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsLongTextRepository<MysqlLongTexts>(sqlClient, MysqlLongTexts)
