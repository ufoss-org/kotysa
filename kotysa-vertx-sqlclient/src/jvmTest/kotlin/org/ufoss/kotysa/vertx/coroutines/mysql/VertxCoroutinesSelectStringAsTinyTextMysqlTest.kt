/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlTinyTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextTest


class VertxCoroutinesSelectStringAsTinyTextMysqlTest :
    AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectStringAsTinyText>(),
    CoroutinesSelectStringAsTinyTextTest<MysqlTinyTexts, UserRepositoryJdbcMysqlSelectStringAsTinyText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsTinyText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsTinyText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsTinyTextRepository<MysqlTinyTexts>(sqlClient, MysqlTinyTexts)
