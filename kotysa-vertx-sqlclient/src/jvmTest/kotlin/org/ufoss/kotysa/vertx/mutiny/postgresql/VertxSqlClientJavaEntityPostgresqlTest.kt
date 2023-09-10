/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaUserRepository

class VertxSqlClientJavaEntityPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<JavaUserPostgresqlRepository>(),
    MutinyJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
