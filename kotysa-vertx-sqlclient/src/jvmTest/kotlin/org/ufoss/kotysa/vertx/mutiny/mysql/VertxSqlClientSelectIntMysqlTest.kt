/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.MysqlInts
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntTest

@Order(1)
class VertxSqlClientSelectIntMysqlTest :
    AbstractVertxSqlClientMysqlTest<SelectIntRepositoryMysqlSelect>(),
    MutinySelectIntTest<MysqlInts, SelectIntRepositoryMysqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        SelectIntRepositoryMysqlSelect(sqlClient)
}

class SelectIntRepositoryMysqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntRepository<MysqlInts>(sqlClient, MysqlInts)
