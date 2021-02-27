/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc
/*
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

abstract class JavaUserRepository(client: DatabaseClient, tables: Tables) : Repository {

    private val sqlClient = client.sqlClient(tables)

    override fun init() {
        createTable()
                .then(insert())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTable() = sqlClient.createTable<JavaUser>()

    fun insert() = sqlClient.insert(javaJdoe, javaBboss)

    fun deleteAll() = sqlClient.deleteAllFromTable<JavaUser>()

    fun selectAll() = sqlClient.selectAll<JavaUser>()

    fun selectFirstByFirstname(firstname: String) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getFirstname] eq firstname }
            .fetchFirst()

    fun selectByAlias1(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias1] eq alias }
            .fetchAll()

    fun selectByAlias2(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias2] eq alias }
            .fetchAll()

    fun selectByAlias3(alias: String?) = sqlClient.select<JavaUser>()
            .where { it[JavaUser::getAlias3] eq alias }
            .fetchAll()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[JavaUser::getFirstname]} ${it[JavaUser::getLastname]}",
                        it[JavaUser::getAlias1])
            }.fetchAll()
}
*/