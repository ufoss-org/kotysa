/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongMysqlTest : AbstractVertxSqlClientMysqlTest<SelectLongRepositoryMysqlSelect>(),
    MutinySelectLongTest<MysqlLongs, SelectLongRepositoryMysqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectLongRepositoryMysqlSelect(sqlClient)
}

class SelectLongRepositoryMysqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLongRepository<MysqlLongs>(sqlClient, MysqlLongs)
