/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientJavaEntityMariadbTest : AbstractVertxSqlClientMariadbTest<JavaUserMariadbRepository>(),
    MutinyJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
