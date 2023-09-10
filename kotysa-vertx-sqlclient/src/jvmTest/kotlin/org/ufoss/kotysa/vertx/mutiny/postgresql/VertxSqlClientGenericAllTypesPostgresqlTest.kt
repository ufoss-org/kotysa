/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyGenericAllTypesRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyGenericAllTypesTest

class VertxSqlClientGenericAllTypesPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<MutinyGenericAllTypesRepository>(), MutinyGenericAllTypesTest {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        MutinyGenericAllTypesRepository(sqlClient)
}
