/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

private val logger = Logger.of<DefaultSqlClientDeleteOrUpdate>()


public open class DefaultSqlClientDeleteOrUpdate protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any> internal constructor(
        override val tables: Tables,
        /**
         * targeted table to update
         */
        public val table: KotysaTable<T>,
        override val dbAccessType: DbAccessType,
        override val module: Module,
    ) : DefaultSqlClientCommon.Properties {
        public val setValues: MutableMap<Column<T, *>, Any?> = mutableMapOf()
        override val parameters: MutableList<Any> = mutableListOf()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType> = mutableListOf()

        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override var index: Int = 0
    }

    public interface WithProperties<T : Any> {
        public val properties: Properties<T>
    }

    public abstract class FirstDeleteOrUpdate<T : Any, U : FromTable<T, U>, V : SqlClientQuery.Where<V>> protected constructor(
            private val dbAccessType: DbAccessType,
            private val module: Module,
    ) : FromTableWhereable<T, U, V>(), FromTable<T, U> {
        protected abstract val tables: Tables
        protected abstract val table: Table<T>

        override val properties: Properties<T> by lazy {
            val kotysaTable = tables.getTable(table)
            val properties = Properties(tables, kotysaTable, dbAccessType, module)
            // init availableColumns with table columns
            addFromTable(properties, kotysaTable)
            properties
        }
    }

    public abstract class DeleteOrUpdate<T : Any, U : FromTable<T, U>, V : SqlClientQuery.Where<V>> protected constructor(
    ) : FromTableWhereable<T, U, V>(), FromTable<T, U>


    public abstract class Update<T : Any, U : FromTable<T, U>, V : SqlClientQuery.Where<V>,
            X : SqlClientQuery.Update<T, X>> protected constructor(dbAccessType: DbAccessType, module: Module)
        : FirstDeleteOrUpdate<T, U, V>(dbAccessType, module), SqlClientQuery.Update<T, X> {

        protected abstract val update: X

        private val updateOpStringColumnNotNull: UpdateOpColumn<T, X, String> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpStringColumnNullable: UpdateOpColumn<T, X, String?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNotNull: UpdateOpColumn<T, X, LocalDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNullable: UpdateOpColumn<T, X, LocalDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNotNull: UpdateOpColumn<T, X, kotlinx.datetime.LocalDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNullable: UpdateOpColumn<T, X, kotlinx.datetime.LocalDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNotNull: UpdateOpColumn<T, X, LocalDate> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNullable: UpdateOpColumn<T, X, LocalDate?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNotNull: UpdateOpColumn<T, X, kotlinx.datetime.LocalDate> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNullable: UpdateOpColumn<T, X, kotlinx.datetime.LocalDate?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNotNull: UpdateOpColumn<T, X, OffsetDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNullable: UpdateOpColumn<T, X, OffsetDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNotNull: UpdateOpColumn<T, X, LocalTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNullable: UpdateOpColumn<T, X, LocalTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpBooleanColumnNotNull: UpdateOpColumn<T, X, Boolean> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpIntColumnNotNull: UpdateOpColumn<T, X, Int> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpIntColumnNullable: UpdateOpColumn<T, X, Int?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpBigIntColumnNotNull: UpdateOpColumn<T, X, Long> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpBigIntColumnNullable: UpdateOpColumn<T, X, Long?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpUuidColumnNotNull: UpdateOpColumn<T, X, UUID> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpUuidColumnNullable: UpdateOpColumn<T, X, UUID?> by lazy {
            UpdateOpColumn(update, properties)
        }

        override infix fun set(stringColumnNotNull: StringColumnNotNull<T>): UpdateOpColumn<T, X, String> =
                updateOpStringColumnNotNull.apply { this.column = stringColumnNotNull }

        override infix fun set(stringColumnNullable: StringColumnNullable<T>): UpdateOpColumn<T, X, String?> =
                updateOpStringColumnNullable.apply { this.column = stringColumnNullable }

        override infix fun set(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, X, LocalDateTime> =
                updateOpLocalDateTimeColumnNotNull.apply { this.column = localDateTimeColumnNotNull }

        override infix fun set(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): UpdateOpColumn<T, X, LocalDateTime?> =
                updateOpLocalDateTimeColumnNullable.apply { this.column = localDateTimeColumnNullable }

        override infix fun set(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, X, kotlinx.datetime.LocalDateTime> =
                updateOpKotlinxLocalDateTimeColumnNotNull.apply { this.column = kotlinxLocalDateTimeColumnNotNull }

        override infix fun set(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): UpdateOpColumn<T, X, kotlinx.datetime.LocalDateTime?> =
                updateOpKotlinxLocalDateTimeColumnNullable.apply { this.column = kotlinxLocalDateTimeColumnNullable }

        override infix fun set(localDateColumnNotNull: LocalDateColumnNotNull<T>): UpdateOpColumn<T, X, LocalDate> =
                updateOpLocalDateColumnNotNull.apply { this.column = localDateColumnNotNull }

        override infix fun set(localDateColumnNullable: LocalDateColumnNullable<T>): UpdateOpColumn<T, X, LocalDate?> =
                updateOpLocalDateColumnNullable.apply { this.column = localDateColumnNullable }

        override infix fun set(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): UpdateOpColumn<T, X, kotlinx.datetime.LocalDate> =
                updateOpKotlinxLocalDateColumnNotNull.apply { this.column = kotlinxLocalDateColumnNotNull }

        override infix fun set(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): UpdateOpColumn<T, X, kotlinx.datetime.LocalDate?> =
                updateOpKotlinxLocalDateColumnNullable.apply { this.column = kotlinxLocalDateColumnNullable }

        override infix fun set(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): UpdateOpColumn<T, X, OffsetDateTime> =
                updateOpOffsetDateTimeColumnNotNull.apply { this.column = offsetDateTimeColumnNotNull }

        override infix fun set(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): UpdateOpColumn<T, X, OffsetDateTime?> =
                updateOpOffsetDateTimeColumnNullable.apply { this.column = offsetDateTimeColumnNullable }

        override infix fun set(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): UpdateOpColumn<T, X, LocalTime> =
                updateOpLocalTimeColumnNotNull.apply { this.column = localTimeColumnNotNull }

        override infix fun set(localTimeColumnNullable: LocalTimeColumnNullable<T>): UpdateOpColumn<T, X, LocalTime?> =
                updateOpLocalTimeColumnNullable.apply { this.column = localTimeColumnNullable }

        override infix fun set(booleanColumnNotNull: BooleanColumnNotNull<T>): UpdateOpColumn<T, X, Boolean> =
                updateOpBooleanColumnNotNull.apply { this.column = booleanColumnNotNull }

        override infix fun set(intColumnNotNull: IntColumnNotNull<T>): UpdateOpColumn<T, X, Int> =
                updateOpIntColumnNotNull.apply { this.column = intColumnNotNull }

        override infix fun set(intColumnNullable: IntColumnNullable<T>): UpdateOpColumn<T, X, Int?> =
                updateOpIntColumnNullable.apply { this.column = intColumnNullable }

        override infix fun set(bigIntColumnNotNull: LongColumnNotNull<T>): UpdateOpColumn<T, X, Long> =
                updateOpBigIntColumnNotNull.apply { this.column = bigIntColumnNotNull }

        override infix fun set(bigIntColumnNullable: LongColumnNullable<T>): UpdateOpColumn<T, X, Long?> =
                updateOpBigIntColumnNullable.apply { this.column = bigIntColumnNullable }

        override infix fun set(uuidColumnNotNull: UuidColumnNotNull<T>): UpdateOpColumn<T, X, UUID> =
                updateOpUuidColumnNotNull.apply { this.column = uuidColumnNotNull }

        override infix fun set(uuidColumnNullable: UuidColumnNullable<T>): UpdateOpColumn<T, X, UUID?> =
                updateOpUuidColumnNullable.apply { this.column = uuidColumnNullable }
    }

    public class UpdateOpColumn<T : Any, U : SqlClientQuery.Update<T, U>, V> internal constructor(
            private val update: U,
            private val properties: Properties<T>,
    ) : SqlClientQuery.UpdateOpColumn<T, U, V> {
        internal lateinit var column: Column<T, *>

        override fun eq(value: V): U = with(properties) {
            setValues[column] = value
            update
        }
    }

    public abstract class Where<T : Any, U : SqlClientQuery.Where<U>> : DefaultSqlClientCommon.Where<U>(), WithProperties<T>, Return<T>

    public interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {

        public fun deleteFromTableSql(): String = with(properties) {
            val deleteSql = "DELETE FROM ${table.name}"
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

            "$deleteSql $joinsAndWheres"
        }

        public fun updateTableSql(): String = with(properties) {
            val updateSql = "UPDATE ${table.name}"
            val setSql = setValues.keys.joinToString(prefix = "SET ") { column ->
                "${column.getKotysaColumn(properties.availableColumns).name} = ${variable()}"
            }
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $updateSql $setSql $joinsAndWheres" }

            "$updateSql $setSql $joinsAndWheres"
        }

        /**
         * Handle joins as EXISTS + nested SELECT
         * Then other WHERE clauses
         */
        public fun joinsWithExistsAndWheres(withWhere: Boolean = true): String = with(properties) {
            val joins = joinsWithExists()

            var wheres = wheres(false)

            if (joins.isEmpty() && wheres.isEmpty()) {
                ""
            } else {
                val prefix = if (withWhere) {
                    "WHERE "
                } else {
                    ""
                }
                if (joins.isNotEmpty()) {
                    if (wheres.isNotEmpty()) {
                        wheres = "AND $wheres"
                    }
                    "$prefix$joins $wheres )"
                } else {
                    "$prefix$wheres"
                }
            }
        }

        /**
         * Handle froms as EXISTS + nested SELECT
         */
        private fun joinsWithExists() = with(properties) {
            val rootJoinClauses = (fromClauses[0] as FromClauseTable).joinClauses
            if (fromClauses.size > 1 || rootJoinClauses.isNotEmpty()) {
                // fixme handle other cases
                if (rootJoinClauses.isNotEmpty()) {
                    val firstFroms = rootJoinClauses
                            .joinToString { joinClause ->
                                joinClause.table.getFieldName(tables.allTables)
                            }
                    val wheres = rootJoinClauses
                            .flatMap { joinClause -> joinClause.references.asIterable() }
                            .joinToString(" AND ", "(", ")") { joinClause ->
                                "${joinClause.key.getFieldName(tables.allColumns, tables.dbType)} = " +
                                        joinClause.value.getFieldName(tables.allColumns, tables.dbType)
                            }
                    // remaining froms
                    fromClauses.removeAt(0)
                    val froms = froms(false)

                    "EXISTS ( SELECT * FROM $firstFroms $froms WHERE $wheres"
                } else {
                    // remaining froms
                    fromClauses.removeAt(0)
                    val froms = froms()
                    "EXISTS ( SELECT * $froms"
                }

            } else {
                ""
            }
        }
    }
}
