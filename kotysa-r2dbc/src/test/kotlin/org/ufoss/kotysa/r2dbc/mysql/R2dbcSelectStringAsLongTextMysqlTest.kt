/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsLongTextTest


class R2dbcSelectStringAsLongTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectStringAsLongText>(),
    CoroutinesSelectStringAsLongTextTest<MysqlLongTexts, UserRepositoryJdbcMysqlSelectStringAsLongText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsLongText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsLongText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsLongTextRepository<MysqlLongTexts>(sqlClient, MysqlLongTexts)
