/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlTinyTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTinyTextTest


class R2dbcSelectStringAsTinyTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectStringAsTinyText>(),
    CoroutinesSelectStringAsTinyTextTest<MysqlTinyTexts, UserRepositoryJdbcMysqlSelectStringAsTinyText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsTinyText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsTinyText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsTinyTextRepository<MysqlTinyTexts>(sqlClient, MysqlTinyTexts)
