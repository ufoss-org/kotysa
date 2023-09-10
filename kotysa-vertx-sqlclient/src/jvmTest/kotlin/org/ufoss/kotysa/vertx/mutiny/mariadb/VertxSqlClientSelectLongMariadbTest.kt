/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongMariadbTest : AbstractVertxSqlClientMariadbTest<SelectLongRepositoryMariadbSelect>(),
    MutinySelectLongTest<MariadbLongs, SelectLongRepositoryMariadbSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectLongRepositoryMariadbSelect(sqlClient)
}

class SelectLongRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLongRepository<MariadbLongs>(sqlClient, MariadbLongs)
