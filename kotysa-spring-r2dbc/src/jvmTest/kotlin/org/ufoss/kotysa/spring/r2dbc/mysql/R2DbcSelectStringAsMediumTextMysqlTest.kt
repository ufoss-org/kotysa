/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsMediumTextTest

class R2DbcSelectStringAsMediumTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectStringAsMediumText>(),
    ReactorSelectStringAsMediumTextTest<MysqlMediumTexts, UserRepositoryR2dbcMysqlSelectStringAsMediumText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectStringAsMediumText(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectStringAsMediumText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsMediumTextRepository<MysqlMediumTexts>(sqlClient, MysqlMediumTexts)
