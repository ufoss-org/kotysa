/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlTinyTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTinyTextTest

class R2DbcSelectStringAsTinyTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectStringAsTinyText>(),
    ReactorSelectStringAsTinyTextTest<MysqlTinyTexts, UserRepositoryR2dbcMysqlSelectStringAsTinyText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectStringAsTinyText(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectStringAsTinyText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsTinyTextRepository<MysqlTinyTexts>(sqlClient, MysqlTinyTexts)
