/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlTexts
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringAsTextTest


class R2dbcSelectStringAsTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectStringAsText>(),
    CoroutinesSelectStringAsTextTest<MysqlTexts, UserRepositoryJdbcMysqlSelectStringAsText,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcMysqlSelectStringAsText(sqlClient)
}

class UserRepositoryJdbcMysqlSelectStringAsText(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringAsTextRepository<MysqlTexts>(sqlClient, MysqlTexts)
