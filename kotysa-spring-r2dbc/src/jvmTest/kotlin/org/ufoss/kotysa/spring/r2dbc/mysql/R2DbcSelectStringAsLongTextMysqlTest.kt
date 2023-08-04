/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsLongTextTest

class R2DbcSelectStringAsLongTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectStringAsLongText>(),
    ReactorSelectStringAsLongTextTest<MysqlLongTexts, UserRepositoryR2dbcMysqlSelectStringAsLongText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectStringAsLongText(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectStringAsLongText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsLongTextRepository<MysqlLongTexts>(sqlClient, MysqlLongTexts)
