/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeTest

class VertxSqlClientSelectKotlinxLocalTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<KotlinxLocalTimePostgresqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimePostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = KotlinxLocalTimePostgresqlRepository(sqlClient)
}

class KotlinxLocalTimePostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)
