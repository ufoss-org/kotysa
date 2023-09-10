/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaEntityTest
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyJavaUserRepository
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientJavaEntityMariadbTest : AbstractVertxSqlClientMariadbTest<JavaUserMariadbRepository>(),
    MutinyJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = JavaUserMariadbRepository(sqlClient)
}


class JavaUserMariadbRepository(sqlClient: MutinySqlClient) :
    MutinyJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
