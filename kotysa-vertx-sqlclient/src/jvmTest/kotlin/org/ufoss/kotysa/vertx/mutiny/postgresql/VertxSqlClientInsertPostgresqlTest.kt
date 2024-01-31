/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import io.vertx.pgclient.PgException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertTest

@Order(3)
class VertxSqlClientInsertPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<RepositoryPostgresqlInsert>(),
    MutinyInsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, PostgresqlIntNonNullIds,
            PostgresqlLongNonNullIds, RepositoryPostgresqlInsert> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        RepositoryPostgresqlInsert(sqlClient)

    override val exceptionClass = PgException::class.java
}


class RepositoryPostgresqlInsert(sqlClient: MutinyVertxSqlClient) :
    MutinyInsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, PostgresqlIntNonNullIds,
            PostgresqlLongNonNullIds>(
        sqlClient,
        PostgresqlInts,
        PostgresqlLongs,
        PostgresqlCustomers,
        PostgresqlIntNonNullIds,
        PostgresqlLongNonNullIds
    )
