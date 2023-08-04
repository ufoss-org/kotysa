/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlTexts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringAsTextTest

class R2DbcSelectStringAsTextMysqlTest :
    AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectStringAsText>(),
    ReactorSelectStringAsTextTest<MysqlTexts, UserRepositoryR2dbcMysqlSelectStringAsText,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectStringAsText(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectStringAsText(sqlClient: ReactorSqlClient) :
    ReactorSelectStringAsTextRepository<MysqlTexts>(sqlClient, MysqlTexts)
