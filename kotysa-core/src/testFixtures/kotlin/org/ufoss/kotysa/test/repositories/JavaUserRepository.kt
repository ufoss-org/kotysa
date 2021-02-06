/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.*

abstract class JavaUserRepository<T : JAVA_USER>(
        private val sqlClient: SqlClient,
        private val table: T,
) : Repository {

    override fun init() {
        createTable()
        insert()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTable() = sqlClient createTable table

    fun insert() = sqlClient.insert(javaJdoe, javaBboss)

    fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom table
                    where table.firstname eq firstname
                    ).fetchFirstOrNull()

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
            (sqlClient select { UserDto("${it[table.firstname]} ${it[table.lastname]}", it[table.alias1]) }
                    from table
                    ).fetchAll()
}
