/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.KClass


private fun tableMustBeMapped(tableName: String?) = "Requested table \"$tableName\" is not mapped"

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(table: Table<T>): KotysaTable<T> =
        requireNotNull(this.allTables[table]) { tableMustBeMapped(table.name) } as KotysaTable<T>

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(tableClass: KClass<out T>): KotysaTable<T> =
        requireNotNull(this.allTables.values.first { kClass -> kClass == tableClass }) as KotysaTable<T>

public fun <T : Any> Tables.checkTable(tableClass: KClass<out T>) {
    require(this.allTables.values.any { kClass -> kClass == tableClass }) { tableMustBeMapped(tableClass.qualifiedName) }
}

private val logger = Logger.of<DefaultSqlClient>()


public interface DefaultSqlClient {
    public val tables: Tables

    public fun createTableSql(table: Table<*>): String {
        val kotysaTable = tables.getTable(table)

        val columns = kotysaTable.columns.joinToString { column ->
            if (tables.dbType == DbType.MYSQL && column.sqlType == SqlType.VARCHAR) {
                requireNotNull(column.size) { "Column ${column.name} : Varchar size is required in MySQL" }
            }
            val size = if (column.size != null) "(${column.size})" else ""
            val nullability = if (column.isNullable) "NULL" else "NOT NULL"
            val autoIncrement = if (column.isAutoIncrement && DbType.SQLITE != tables.dbType) {
                // SQLITE : The AUTOINCREMENT keyword imposes extra CPU, memory, disk space, and disk I/O overhead and should be avoided if not strictly needed.
                // It is usually not needed -> https://sqlite.org/autoinc.html
                // if this needs to be added later, sqlite syntax MUST be "column INTEGER PRIMARY KEY AUTOINCREMENT"
                " AUTO_INCREMENT"
            } else {
                ""
            }
            val default = if (column.defaultValue != null) {
                " DEFAULT ${column.defaultValue.defaultValue()}"
            } else {
                ""
            }
            "${column.name} ${column.sqlType.fullType}$size $nullability$autoIncrement$default"
        }

        val pk = kotysaTable.primaryKey
        var primaryKey = if (pk.name != null) {
            "CONSTRAINT ${pk.name} "
        } else {
            ""
        }
        primaryKey += "PRIMARY KEY (${pk.columns.joinToString { it.name }})"

        val foreignKeys =
                if (kotysaTable.foreignKeys.isEmpty()) {
                    ""
                } else {
                    kotysaTable.foreignKeys.joinToString(prefix = ", ") { foreignKey ->
                        var foreignKeyStatement = if (foreignKey.name != null) {
                            "CONSTRAINT ${foreignKey.name} "
                        } else {
                            ""
                        }
                        foreignKeyStatement += "FOREIGN KEY (${foreignKey.columns.joinToString { it.name }})" +
                                " REFERENCES ${foreignKey.referencedTable.name}" +
                                " (${foreignKey.referencedColumns.joinToString { it.name }})"
                        foreignKeyStatement
                    }
                }

        val createTableSql = "CREATE TABLE IF NOT EXISTS ${kotysaTable.name} ($columns, $primaryKey$foreignKeys)"
        logger.debug { "Exec SQL (${tables.dbType.name}) : $createTableSql" }
        return createTableSql
    }

    public fun checkRowsAreMapped(vararg rows: Any) {
        // fail-fast : check that all tables are mapped Tables
        rows.forEach { row -> tables.checkTable(row::class) }
    }

    public fun <T : Any> insertSql(row: T): String {
        val insertSqlQuery = insertSqlQuery(row)
        logger.debug { "Exec SQL (${tables.dbType.name}) : $insertSqlQuery" }
        return insertSqlQuery
    }

    public fun <T : Any> insertSqlDebug(row: T) {
        logger.debug { "Exec SQL (${tables.dbType.name}) : ${insertSqlQuery(row)}" }
    }

    public fun <T : Any> insertSqlQuery(row: T): String {
        val kotysaTable = tables.getTable(row::class)
        val columnNames = mutableSetOf<String>()
        var index = 0
        val values = kotysaTable.columns
                // filter out null values with default value or Serial type
                .filterNot { column ->
                    column.entityGetter(row) == null
                            && (column.defaultValue != null || SqlType.SERIAL == column.sqlType)
                }
                .joinToString { column ->
                    columnNames.add(column.name)
                    if (DbType.SQLITE == tables.dbType) {
                        "?"
                    } else {
                        ":k${index++}"
                    }
                }

        return "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values)"
    }
}

private fun Any?.dbValue(): String = when (this) {
    null -> "null"
    is String -> "$this"
    is Boolean -> "$this"
    is UUID -> "$this"
    is Int -> "$this"
    is LocalDate -> this.format(DateTimeFormatter.ISO_LOCAL_DATE)
    is LocalDateTime -> this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    /*DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .optionalStart()
            .appendFraction(MICRO_OF_SECOND, 0, 6, true)
            .optionalEnd()
            .toFormatter(Locale.ENGLISH))*/
    is LocalTime -> /*"+" + */this.format(DateTimeFormatter.ISO_LOCAL_TIME)
    is OffsetDateTime -> this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    else -> when (this::class.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> this.toString()
        "kotlinx.datetime.LocalDateTime" -> {
            val kotlinxLocalDateTime = this as kotlinx.datetime.LocalDateTime
            if (kotlinxLocalDateTime.second == 0 && kotlinxLocalDateTime.nanosecond == 0) {
                "$kotlinxLocalDateTime:00" // missing seconds
            } else {
                kotlinxLocalDateTime.toString()
            }
        }
        else -> throw RuntimeException("${this.javaClass.canonicalName} is not supported yet")
    }
}

private fun Any?.defaultValue(): String = when (this) {
    is Int -> "$this"
    else -> "'${this.dbValue()}'"
}

/*
public open class DefaultSqlClientCommon protected constructor() {

    public interface Properties {
        public val tables: Tables
        public val whereClauses: MutableList<TypedWhereClause>
        public val joinClauses: MutableList<JoinClause>
        public val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>>
    }

    protected interface Instruction {
        @Suppress("UNCHECKED_CAST")
        public fun <T : Any> addAvailableColumnsFromTable(
                properties: Properties,
                table: KotysaTable<T>
        ) {
            properties.apply {
                if (joinClauses.isEmpty() ||
                        !joinClauses.map { joinClause -> joinClause.table.kotysaTable }.contains(table)) {
                    table.columns.forEach { kotysaColumn ->
                        // 1) add mapped getter
                        availableColumns[kotysaColumn.column] = kotysaColumn

                        /*val getterCallable = kotysaColumn.entityGetter.toCallable()

                        // 2) add overridden parent getters associated with this column
                        table.tableClass.allSuperclasses
                                .flatMap { superClass -> superClass.members }
                                .filter { callable ->
                                    callable.isAbstract
                                            && callable.name == getterCallable.name
                                            && (callable is KProperty1<*, *> || callable.name.startsWith("get"))
                                            && (callable.returnType == getterCallable.returnType
                                            || callable.returnType.classifier is KTypeParameter)
                                }
                                .forEach { callable ->
                                    availableColumns[callable as (Any) -> Any?] = value
                                }*/
                    }
                }
            }
        }
    }

    public interface WithProperties {
        public val properties: Properties
    }

    protected interface Whereable : WithProperties

    protected interface Join : WithProperties, Instruction {
        public fun <T : Any> addJoinClause(dsl: (FieldProvider) -> ColumnField<*, *>, joinClass: KClass<T>, alias: String?, type: JoinType) {
            properties.apply {
                tables.checkTable(joinClass)
                val aliasedTable = AliasedKotysaTable(tables.getTable(joinClass), alias)
                joinClauses.add(JoinDsl(dsl, aliasedTable, type, availableColumns, tables.dbType).initialize())
                addAvailableColumnsFromTable(this, aliasedTable)
            }
        }
    }

    protected interface Where : WithProperties {
        public fun addWhereClause(dsl: WhereDsl.(FieldProvider) -> WhereClause) {
            addClause(dsl, WhereClauseType.WHERE)
        }

        public fun addAndClause(dsl: WhereDsl.(FieldProvider) -> WhereClause) {
            addClause(dsl, WhereClauseType.AND)
        }

        public fun addOrClause(dsl: WhereDsl.(FieldProvider) -> WhereClause) {
            addClause(dsl, WhereClauseType.OR)
        }

        private fun addClause(dsl: WhereDsl.(FieldProvider) -> WhereClause, whereClauseType: WhereClauseType) {
            properties.apply {
                whereClauses.add(TypedWhereClause(
                        WhereDsl(dsl, availableColumns, tables.dbType).initialize(), whereClauseType))
            }
        }
    }

    protected interface TypedWhere<T : Any> : WithProperties {
        public fun addWhereClause(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause) {
            addClause(dsl, WhereClauseType.WHERE)
        }

        public fun addAndClause(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause) {
            addClause(dsl, WhereClauseType.AND)
        }

        public fun addOrClause(dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause) {
            addClause(dsl, WhereClauseType.OR)
        }

        private fun addClause(
                dsl: TypedWhereDsl<T>.(TypedFieldProvider<T>) -> WhereClause,
                whereClauseType: WhereClauseType
        ) {
            properties.apply {
                whereClauses.add(TypedWhereClause(
                        TypedWhereDsl(dsl, availableColumns, tables.dbType).initialize(), whereClauseType))
            }
        }
    }

    public interface Return : WithProperties {

        /**
         * Used exclusively by SqLite
         */
        public fun stringValue(value: Any?): String = value.dbValue()

        public fun joins(): String =
                properties.joinClauses.joinToString { joinClause ->
                    // fixme handle multiple columns
                    val joinedTableFieldName = "${joinClause.table.prefix}.${joinClause.table.primaryKey.columns[0].name}"

                    "${joinClause.type.sql} ${joinClause.table.declaration} ON ${joinClause.field.fieldName} = $joinedTableFieldName"
                }

        public fun wheres(withWhere: Boolean = true, offset: Int = 0): String = with(properties) {
            if (whereClauses.isEmpty()) {
                return ""
            }
            val where = StringBuilder()
            if (withWhere) {
                where.append("WHERE ")
            }
            var index = offset
            whereClauses.forEach { typedWhereClause ->
                where.append(
                        when (typedWhereClause.type) {
                            WhereClauseType.AND -> " AND "
                            WhereClauseType.OR -> " OR "
                            else -> ""
                        }
                )
                where.append("(")
                typedWhereClause.whereClause.apply {
                    where.append(
                            when (operation) {
                                Operation.EQ ->
                                    if (value == null) {
                                        "${field.fieldName} IS NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${field.fieldName} = ?"
                                        } else {
                                            "${field.fieldName} = :k${index++}"
                                        }
                                    }
                                Operation.NOT_EQ ->
                                    if (value == null) {
                                        "${field.fieldName} IS NOT NULL"
                                    } else {
                                        if (DbType.SQLITE == tables.dbType) {
                                            "${field.fieldName} <> ?"
                                        } else {
                                            "${field.fieldName} <> :k${index++}"
                                        }
                                    }
                                Operation.CONTAINS, Operation.STARTS_WITH, Operation.ENDS_WITH ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} LIKE ?"
                                    } else {
                                        "${field.fieldName} LIKE :k${index++}"
                                    }
                                Operation.INF ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} < ?"
                                    } else {
                                        "${field.fieldName} < :k${index++}"
                                    }
                                Operation.INF_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} <= ?"
                                    } else {
                                        "${field.fieldName} <= :k${index++}"
                                    }
                                Operation.SUP ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} > ?"
                                    } else {
                                        "${field.fieldName} > :k${index++}"
                                    }
                                Operation.SUP_OR_EQ ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} >= ?"
                                    } else {
                                        "${field.fieldName} >= :k${index++}"
                                    }
                                Operation.IS ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        "${field.fieldName} IS ?"
                                    } else {
                                        "${field.fieldName} IS :k${index++}"
                                    }
                                Operation.IN ->
                                    if (DbType.SQLITE == tables.dbType) {
                                        // must put as much '?' as
                                        "${field.fieldName} IN (${(value as Collection<*>).joinToString { "?" }})"
                                    } else {
                                        "${field.fieldName} IN (:k${index++})"
                                    }
                            }
                    )
                }
                where.append(")")
            }
            return where.toString()
        }
    }
}*/
