/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.JavaUserRepository
import org.ufoss.kotysa.r2dbc.SpringR2dbcJavaEntityTest
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.JavaUser


class R2DbcJavaEntityPostgresqlTest :
        AbstractR2dbcPostgresqlTest<JavaUserPostgresqlRepository>(), SpringR2dbcJavaEntityTest<JavaUserPostgresqlRepository> {
    override val context = startContext<JavaUserPostgresqlRepository>()
    override val repository = getContextRepository<JavaUserPostgresqlRepository>()
}

private val tables =
        tables().postgresql {
            table<JavaUser> {
                name = "java_users"
                column { it[JavaUser::getLogin].varchar() }
                        .primaryKey()
                column {
                    it[JavaUser::getFirstname].varchar {
                        name = "fname"
                    }
                }
                column {
                    it[JavaUser::getLastname].varchar {
                        name = "lname"
                    }
                }
                column { it[JavaUser::isAdmin].boolean() }
                column { it[JavaUser::getAlias1].varchar() }
                column { it[JavaUser::getAlias2].varchar() }
                column { it[JavaUser::getAlias3].varchar() }
            }
        }


class JavaUserPostgresqlRepository(client: DatabaseClient) : JavaUserRepository(client, tables)
