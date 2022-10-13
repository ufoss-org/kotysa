/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<JavaUserPostgresqlRepository>(),
    MutinyJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
