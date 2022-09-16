/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.dao.NonTransientDataAccessException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.toRow
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
internal sealed class SqlClientSpringJdbc(
    private val client: JdbcOperations,
    override val tables: Tables,
) : DefaultSqlClient {

    override val module = Module.SPRING_JDBC

    /**
     * Computed property : only created once on first call
     */
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations by lazy {
        NamedParameterJdbcTemplate(client)
    }

    protected fun <T : Any> insertProtected(row: T) {
        val table = tables.getTable(row::class)
        val paramSource = paramSource(row, table)

        namedParameterJdbcOperations.update(insertSql(row), paramSource)
    }

    protected fun <T : Any> insertProtected(rows: Array<T>) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        rows.forEach { row -> insertProtected(row) }
    }

    protected fun <T : Any> insertAndReturnProtected(row: T): T {
        val table = tables.getTable(row::class)
        val paramSource = paramSource(row, table)
        return if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            namedParameterJdbcOperations.update(insertSql(row), paramSource)
            fetchLastInserted(row, table)
        } else {
            // other DB types have RETURNING style features
            namedParameterJdbcOperations.queryForObject(
                insertSql(row, true),
                paramSource,
            ) { rs, _ ->
                (table.table as AbstractTable<T>).toField(
                    tables.allColumns,
                    tables.allTables,
                    tables.dbType,
                ).builder.invoke(rs.toRow())
            }!!
        }
    }

    protected fun <T : Any> insertAndReturnProtected(rows: Array<out T>) =
        rows.map { row -> insertAndReturnProtected(row) }

    private fun <T : Any> paramSource(row: T, table: KotysaTable<T>): SqlParameterSource {
        val parameters = MapSqlParameterSource()
        table.columns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .map { column -> tables.getDbValue(column.entityGetter(row)) }
            .forEachIndexed { index, dbValue -> parameters.addValue("k$index", dbValue) }
        return parameters
    }

    private fun <T : Any> fetchLastInserted(row: T, table: KotysaTable<T>): T {
        val pkColumns = table.primaryKey.columns
        val pkFirstColumn = pkColumns.elementAt(0)
        
        val parameters = MapSqlParameterSource()
        if (
            pkColumns.size != 1 ||
            !pkFirstColumn.isAutoIncrement ||
            pkFirstColumn.entityGetter(row) != null
        ) {
            // bind all PK values
            pkColumns
                .map { column -> tables.getDbValue(column.entityGetter(row)) }
                .forEachIndexed { index, dbValue ->
                    parameters.addValue("k${index}", dbValue)
                }
        }

        return namedParameterJdbcOperations.queryForObject(lastInsertedSql(row), parameters) { rs, _ ->
            (table.table as AbstractTable<T>).toField(
                tables.allColumns,
                tables.allTables,
                tables.dbType,
            ).builder.invoke(rs.toRow())
        }!!
    }

    protected fun <T : Any> createTableProtected(table: Table<T>) {
        createTable(table, false)
    }

    protected fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableResult = createTableSql(table, ifNotExists)
        // 1) execute create table
        client.execute(createTableResult.sql)
        // 2) loop to execute create indexes
        createTableResult.createIndexes.forEach { createIndexResult ->
            try {
                client.execute(createIndexResult.sql)
            } catch (ntdae: NonTransientDataAccessException) {
                // if not exists : accept Index already exists error
                if (!ifNotExists || ntdae.message?.contains(createIndexResult.name, true) != true) {
                    throw ntdae
                }
            }
        }
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteSpringJdbc.FirstDelete(namedParameterJdbcOperations, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateSpringJdbc.FirstUpdate(namedParameterJdbcOperations, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(column)

    protected fun <T : Any> selectProtected(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(table)

    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectAndBuild(dsl)

    protected fun selectCountProtected(): SqlClientSelect.Fromable<Long> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectCount<Any>(null)

    protected fun <T : Any> selectCountProtected(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectCount(column)

    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectDistinct(column)

    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectMin(column)

    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectMax(column)

    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectAvg(column)

    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectSum(column)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.From<T> =
        SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectStarFromSubQuery(dsl)
}

internal class H2SqlClientSpringJdbc internal constructor(
    client: JdbcOperations,
    tables: H2Tables,
) : SqlClientSpringJdbc(client, tables), H2SqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MysqlSqlClientSpringJdbc internal constructor(
    client: JdbcOperations,
    tables: MysqlTables,
) : SqlClientSpringJdbc(client, tables), MysqlSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class PostgresqlSqlClientSpringJdbc internal constructor(
    client: JdbcOperations,
    tables: PostgresqlTables,
) : SqlClientSpringJdbc(client, tables), PostgresqlSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MssqlSqlClientSpringJdbc internal constructor(
    client: JdbcOperations,
    tables: MssqlTables,
) : SqlClientSpringJdbc(client, tables), MssqlSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}

internal class MariadbSqlClientSpringJdbc internal constructor(
    client: JdbcOperations,
    tables: MariadbTables,
) : SqlClientSpringJdbc(client, tables), MariadbSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)
}
