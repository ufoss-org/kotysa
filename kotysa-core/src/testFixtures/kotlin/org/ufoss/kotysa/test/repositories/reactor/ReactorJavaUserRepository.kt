/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

abstract class ReactorJavaUserRepository<T : JavaUsers>(
    private val sqlClient: ReactorSqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTable()
                .then(insert().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient.insert(javaJdoe, javaBboss)

    fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom table
                    where table.firstname eq firstname
                    ).fetchFirst()

    fun selectByAlias1(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias1 eq alias
                    ).fetchAll()

    fun selectByAlias2(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias2 eq alias
                    ).fetchAll()

    fun selectByAlias3(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias3 eq alias
                    ).fetchAll()

    fun selectAllMappedToDto() =
            (sqlClient selectAndBuild { UserDto("${it[table.firstname]} ${it[table.lastname]}", it[table.alias1]) }
                    from table
                    ).fetchAll()
}
