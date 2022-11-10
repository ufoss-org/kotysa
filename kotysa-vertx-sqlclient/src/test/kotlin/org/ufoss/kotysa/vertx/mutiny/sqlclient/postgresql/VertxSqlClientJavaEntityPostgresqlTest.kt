/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*

class VertxSqlClientJavaEntityPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<JavaUserPostgresqlRepository>(),
    MutinyJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
