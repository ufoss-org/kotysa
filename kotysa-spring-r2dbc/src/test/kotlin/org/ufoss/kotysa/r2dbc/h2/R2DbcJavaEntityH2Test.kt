/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.JavaUserRepository
import org.ufoss.kotysa.r2dbc.SpringR2dbcJavaEntityTest
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.JavaUser


class R2DbcJavaEntityH2Test :
        AbstractR2dbcH2Test<JavaUserH2Repository>(), SpringR2dbcJavaEntityTest<JavaUserH2Repository> {
    override var context = startContext<JavaUserH2Repository>()
    override var repository = getContextRepository<JavaUserH2Repository>()
}

private val tables =
        tables().h2Old {
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


class JavaUserH2Repository(client: DatabaseClient) : JavaUserRepository(client, tables)
