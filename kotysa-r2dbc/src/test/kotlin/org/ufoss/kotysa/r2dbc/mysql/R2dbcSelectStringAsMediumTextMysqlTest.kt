/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsMediumTextTest


class R2dbcSelectStringAsMediumTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectStringAsMediumText>(),
    CoroutinesSelectStringAsMediumTextTest<MysqlMediumTexts, UserRepositoryJdbcMysqlSelectStringAsMediumText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsMediumText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsMediumText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsMediumTextRepository<MysqlMediumTexts>(sqlClient, MysqlMediumTexts)
