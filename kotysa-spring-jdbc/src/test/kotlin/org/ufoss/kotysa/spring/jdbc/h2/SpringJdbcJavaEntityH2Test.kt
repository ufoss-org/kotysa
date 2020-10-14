/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.JavaUserRepository
import org.ufoss.kotysa.spring.jdbc.SpringJdbcJavaEntityTest
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.test.JavaUser


class SpringJdbcJavaEntityH2Test :
        AbstractSpringJdbcH2Test<JavaUserH2Repository>(), SpringJdbcJavaEntityTest<JavaUserH2Repository> {
    override val context = startContext<JavaUserH2Repository>()
    override val repository = getContextRepository<JavaUserH2Repository>()
}

private val tables =
        tables().h2 {
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


class JavaUserH2Repository(client: JdbcOperations) : JavaUserRepository(client, tables)
