/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import io.vertx.mysqlclient.MySQLException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest


class VertxCoroutinesSelectMariadbTest : AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelect>(),
    CoroutinesSelectTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryJdbcMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMariadbSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(MySQLException::class.java)
    }
}

class UserRepositoryJdbcMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
