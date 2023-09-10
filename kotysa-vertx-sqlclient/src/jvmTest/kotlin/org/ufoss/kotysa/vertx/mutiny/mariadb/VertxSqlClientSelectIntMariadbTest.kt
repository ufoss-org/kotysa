/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.MariadbInts
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntTest

@Order(1)
class VertxSqlClientSelectIntMariadbTest :
    AbstractVertxSqlClientMariadbTest<SelectIntRepositoryMariadbSelect>(),
    MutinySelectIntTest<MariadbInts, SelectIntRepositoryMariadbSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        SelectIntRepositoryMariadbSelect(sqlClient)
}

class SelectIntRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntRepository<MariadbInts>(sqlClient, MariadbInts)
