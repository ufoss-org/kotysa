/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientJavaEntityMysqlTest : AbstractVertxSqlClientMysqlTest<JavaUserMysqlRepository>(),
    MutinyJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
