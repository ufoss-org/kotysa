/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import org.ufoss.kotysa.test.*

abstract class VertxJavaUserRepository<T : JAVA_USER>(
    private val sqlClient: MutinySqlClient,
    private val table: T,
) : Repository {

    override fun init() {
        createTable()
            .chain { -> insert() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
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
