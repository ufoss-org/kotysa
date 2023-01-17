/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMysqlTest : AbstractVertxSqlClientMysqlTest<JavaUserMysqlRepository>(),
    MutinyJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
