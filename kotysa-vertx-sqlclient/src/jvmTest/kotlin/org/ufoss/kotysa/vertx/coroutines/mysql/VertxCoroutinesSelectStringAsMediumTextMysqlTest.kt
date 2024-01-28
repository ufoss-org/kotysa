/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextTest


class VertxCoroutinesSelectStringAsMediumTextMysqlTest :
    AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectStringAsMediumText>(),
    CoroutinesSelectStringAsMediumTextTest<MysqlMediumTexts, UserRepositoryJdbcMysqlSelectStringAsMediumText,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsMediumText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsMediumText(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringAsMediumTextRepository<MysqlMediumTexts>(sqlClient, MysqlMediumTexts)
