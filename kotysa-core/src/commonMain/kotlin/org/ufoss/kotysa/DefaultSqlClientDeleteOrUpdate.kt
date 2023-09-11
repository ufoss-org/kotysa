/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.math.BigDecimal
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
        override val parameters: MutableList<Any?> = mutableListOf()
        override val fromClauses: MutableList<FromClause> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType> = mutableListOf()

        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
        override var index: Int = 0

        public val updateClauses: MutableList<UpdateClause<T>> = mutableListOf()
    }

    public interface WithProperties<T : Any> {
        public val properties: Properties<T>
    }

    public abstract class FirstDeleteOrUpdate<T : Any, U : SqlClientQuery.Where<U>>
    protected constructor(
        private val dbAccessType: DbAccessType,
        private val module: Module,
    ) : FromTableWhereable<T, U>(), FromTable<T> {
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

    public abstract class DeleteOrUpdate<T : Any, U : SqlClientQuery.Where<U>>
    protected constructor(
    ) : FromTableWhereable<T, U>(), FromTable<T>


    public abstract class Update<T : Any, U : SqlClientQuery.Where<U>, V : SqlClientQuery.Update<T, V, W>,
            W : UpdateInt<T, V, W>> protected constructor(
        dbAccessType: DbAccessType,
        module: Module
    ) : FirstDeleteOrUpdate<T, U>(dbAccessType, module), SqlClientQuery.Update<T, V, W>, UpdateInt<T, V, W> {

        protected abstract val update: V
        protected abstract val updateInt: W

        private val updateOpStringColumnNotNull: UpdateOpColumn<T, V, String, StringColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpStringColumnNullable: UpdateOpColumn<T, V, String?, StringColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNotNull
                : UpdateOpColumn<T, V, LocalDateTime, LocalDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNullable
                : UpdateOpColumn<T, V, LocalDateTime?, LocalDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNotNull
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalDateTime, KotlinxLocalDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNullable
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalDateTime?, KotlinxLocalDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNotNull: UpdateOpColumn<T, V, LocalDate, LocalDateColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNullable: UpdateOpColumn<T, V, LocalDate?, LocalDateColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNotNull
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalDate, KotlinxLocalDateColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNullable
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalDate?, KotlinxLocalDateColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNotNull
                : UpdateOpColumn<T, V, OffsetDateTime, OffsetDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNullable
                : UpdateOpColumn<T, V, OffsetDateTime?, OffsetDateTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNotNull: UpdateOpColumn<T, V, LocalTime, LocalTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNullable: UpdateOpColumn<T, V, LocalTime?, LocalTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalTimeColumnNotNull
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalTime, KotlinxLocalTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalTimeColumnNullable
                : UpdateOpColumn<T, V, kotlinx.datetime.LocalTime?, KotlinxLocalTimeColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpBooleanColumnNotNull: UpdateOpColumn<T, V, Boolean, BooleanColumnNotNull<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpIntColumnNotNull: UpdateOpIntColumn<T, V, Int, IntColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpIntColumnNullable: UpdateOpIntColumn<T, V, Int?, IntColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpLongColumnNotNull: UpdateOpIntColumn<T, V, Long, LongColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpLongColumnNullable: UpdateOpIntColumn<T, V, Long?, LongColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpFloatColumnNotNull: UpdateOpIntColumn<T, V, Float, FloatColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpFloatColumnNullable: UpdateOpIntColumn<T, V, Float?, FloatColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpDoubleColumnNotNull: UpdateOpIntColumn<T, V, Double, DoubleColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpDoubleColumnNullable: UpdateOpIntColumn<T, V, Double?, DoubleColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpBigDecimalColumnNotNull: UpdateOpIntColumn<T, V, BigDecimal, BigDecimalColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpBigDecimalColumnNullable: UpdateOpIntColumn<T, V, BigDecimal?, BigDecimalColumn<*>, W> by lazy {
            UpdateOpIntColumn(update, updateInt, properties)
        }
        private val updateOpUuidColumnNotNull: UpdateOpColumn<T, V, UUID, UuidColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpUuidColumnNullable: UpdateOpColumn<T, V, UUID?, UuidColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpByteArrayColumnNotNull: UpdateOpColumn<T, V, ByteArray, ByteArrayColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpByteArrayColumnNullable: UpdateOpColumn<T, V, ByteArray?, ByteArrayColumn<*>, W> by lazy {
            UpdateOpColumn(update, properties)
        }

        override fun set(stringColumnNotNull: StringColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, String, StringColumn<*>, W> =
            updateOpStringColumnNotNull.apply { this.column = stringColumnNotNull }

        override fun set(stringColumnNullable: StringColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, String?, StringColumn<*>, W> =
            updateOpStringColumnNullable.apply { this.column = stringColumnNullable }

        override fun set(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalDateTime, LocalDateTimeColumn<*>, W> =
            updateOpLocalDateTimeColumnNotNull.apply { this.column = localDateTimeColumnNotNull }

        override fun set(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalDateTime?, LocalDateTimeColumn<*>, W> =
            updateOpLocalDateTimeColumnNullable.apply { this.column = localDateTimeColumnNullable }

        override fun set(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalDateTime, KotlinxLocalDateTimeColumn<*>, W> =
            updateOpKotlinxLocalDateTimeColumnNotNull.apply { this.column = kotlinxLocalDateTimeColumnNotNull }

        override fun set(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalDateTime?, KotlinxLocalDateTimeColumn<*>, W> =
            updateOpKotlinxLocalDateTimeColumnNullable.apply { this.column = kotlinxLocalDateTimeColumnNullable }

        override fun set(localDateColumnNotNull: LocalDateColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalDate, LocalDateColumn<*>, W> =
            updateOpLocalDateColumnNotNull.apply { this.column = localDateColumnNotNull }

        override fun set(localDateColumnNullable: LocalDateColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalDate?, LocalDateColumn<*>, W> =
            updateOpLocalDateColumnNullable.apply { this.column = localDateColumnNullable }

        override fun set(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalDate, KotlinxLocalDateColumn<*>, W> =
            updateOpKotlinxLocalDateColumnNotNull.apply { this.column = kotlinxLocalDateColumnNotNull }

        override fun set(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalDate?, KotlinxLocalDateColumn<*>, W> =
            updateOpKotlinxLocalDateColumnNullable.apply { this.column = kotlinxLocalDateColumnNullable }

        override fun set(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, OffsetDateTime, OffsetDateTimeColumn<*>, W> =
            updateOpOffsetDateTimeColumnNotNull.apply { this.column = offsetDateTimeColumnNotNull }

        override fun set(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, OffsetDateTime?, OffsetDateTimeColumn<*>, W> =
            updateOpOffsetDateTimeColumnNullable.apply { this.column = offsetDateTimeColumnNullable }

        override fun set(localTimeColumnNotNull: LocalTimeColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalTime, LocalTimeColumn<*>, W> =
            updateOpLocalTimeColumnNotNull.apply { this.column = localTimeColumnNotNull }

        override fun set(localTimeColumnNullable: LocalTimeColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, LocalTime?, LocalTimeColumn<*>, W> =
            updateOpLocalTimeColumnNullable.apply { this.column = localTimeColumnNullable }

        override fun set(kotlinxLocalTimeColumnNotNull: KotlinxLocalTimeColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalTime, KotlinxLocalTimeColumn<*>, W> =
            updateOpKotlinxLocalTimeColumnNotNull.apply { this.column = kotlinxLocalTimeColumnNotNull }

        override fun set(kotlinxLocalTimeColumnNullable: KotlinxLocalTimeColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, kotlinx.datetime.LocalTime?, KotlinxLocalTimeColumn<*>, W> =
            updateOpKotlinxLocalTimeColumnNullable.apply { this.column = kotlinxLocalTimeColumnNullable }
        
        override fun set(booleanColumnNotNull: BooleanColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, Boolean, BooleanColumnNotNull<*>, W> =
            updateOpBooleanColumnNotNull.apply { this.column = booleanColumnNotNull }

        override fun set(intColumnNotNull: IntColumnNotNull<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Int, IntColumn<*>, W> =
            updateOpIntColumnNotNull.apply { this.column = intColumnNotNull }

        override fun set(intColumnNullable: IntColumnNullable<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Int?, IntColumn<*>, W> =
            updateOpIntColumnNullable.apply { this.column = intColumnNullable }

        override fun set(longColumnNotNull: LongColumnNotNull<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Long, LongColumn<*>, W> =
            updateOpLongColumnNotNull.apply { this.column = longColumnNotNull }

        override fun set(longColumnNullable: LongColumnNullable<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Long?, LongColumn<*>, W> =
            updateOpLongColumnNullable.apply { this.column = longColumnNullable }

        override fun set(floatColumnNotNull: FloatColumnNotNull<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Float, FloatColumn<*>, W> =
            updateOpFloatColumnNotNull.apply { this.column = floatColumnNotNull }

        override fun set(floatColumnNullable: FloatColumnNullable<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Float?, FloatColumn<*>, W> =
            updateOpFloatColumnNullable.apply { this.column = floatColumnNullable }

        override fun set(doubleColumnNotNull: DoubleColumnNotNull<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Double, DoubleColumn<*>, W> =
            updateOpDoubleColumnNotNull.apply { this.column = doubleColumnNotNull }

        override fun set(doubleColumnNullable: DoubleColumnNullable<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, Double?, DoubleColumn<*>, W> =
            updateOpDoubleColumnNullable.apply { this.column = doubleColumnNullable }

        override fun set(bigDecimalColumnNotNull: BigDecimalColumnNotNull<T>)
                : SqlClientQuery.UpdateOpIntColumn<T, V, BigDecimal, BigDecimalColumn<*>, W> =
            updateOpBigDecimalColumnNotNull.apply { this.column = bigDecimalColumnNotNull }

        override fun set(bigDecimalColumnNullable: BigDecimalColumnNullable<T>)
        : SqlClientQuery.UpdateOpIntColumn<T, V, BigDecimal?, BigDecimalColumn<*>, W> =
            updateOpBigDecimalColumnNullable.apply { this.column = bigDecimalColumnNullable }

        override fun set(uuidColumnNotNull: UuidColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, UUID, UuidColumn<*>, W> =
            updateOpUuidColumnNotNull.apply { this.column = uuidColumnNotNull }

        override fun set(uuidColumnNullable: UuidColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, UUID?, UuidColumn<*>, W> =
            updateOpUuidColumnNullable.apply { this.column = uuidColumnNullable }

        override fun set(byteArrayColumnNotNull: ByteArrayColumnNotNull<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, ByteArray, ByteArrayColumn<*>, W> =
            updateOpByteArrayColumnNotNull.apply { this.column = byteArrayColumnNotNull }

        override fun set(byteArrayColumnNullable: ByteArrayColumnNullable<T>)
                : SqlClientQuery.UpdateOpColumn<T, V, ByteArray?, ByteArrayColumn<*>, W> =
            updateOpByteArrayColumnNullable.apply { this.column = byteArrayColumnNullable }

        override fun plus(increment: Int): V {
            val lastUpdate = properties.updateClauses.last() as UpdateClauseColumn
            lastUpdate.increment = increment
            return update
        }

        override fun minus(decrement: Int): V {
            val lastUpdate = properties.updateClauses.last() as UpdateClauseColumn
            lastUpdate.increment = -decrement
            return update
        }
    }

    internal class UpdateOpColumn<T : Any, U : SqlClientQuery.Update<T, U, X>, V, W : Column<*, *>,
            X : UpdateInt<T, U, X>> internal constructor(
        internal val update: U,
        internal val properties: Properties<T>,
    ) : SqlClientQuery.UpdateOpColumn<T, U, V, W, X> {
        internal lateinit var column: DbColumn<T, *>

        override fun eq(value: V): U = with(properties) {
            updateClauses.add(UpdateClauseValue(column))
            parameters.add(value)
            update
        }

        override fun eq(otherColumn: W): U = with(properties) {
            updateClauses.add(UpdateClauseColumn(column, otherColumn))
            update
        }
    }

    internal class UpdateOpIntColumn<T : Any, U : SqlClientQuery.Update<T, U, X>, V, W : Column<*, *>,
            X : UpdateInt<T, U, X>> internal constructor(
        private val update: U,
        private val updateInt: X,
        private val properties: Properties<T>,
    ) : SqlClientQuery.UpdateOpIntColumn<T, U, V, W, X> {
        internal lateinit var column: DbColumn<T, *>

        override fun eq(value: V): U = with(properties) {
            updateClauses.add(UpdateClauseValue(column))
            parameters.add(value)
            update
        }

        override fun eq(otherColumn: W): X = with(properties) {
            updateClauses.add(UpdateClauseColumn(column, otherColumn))
            updateInt
        }
    }

    public abstract class Where<T : Any, U : SqlClientQuery.Where<U>> : DefaultSqlClientCommon.Where<U>(),
        WithProperties<T>, Return<T>

    public interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {

        public fun deleteFromTableSql(): String = with(properties) {
            val deleteSql = "DELETE FROM ${table.name}"
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

            "$deleteSql $joinsAndWheres"
        }

        public fun updateTableSql(): String = with(properties) {
            val updateSql = "UPDATE ${table.name}"
            val setSql = updateClauses.joinToString(prefix = "SET ") { updateClause ->
                updateClause.run {
                    val fieldName = column.getKotysaColumn(availableColumns).name
                    when (this) {
                        is UpdateClauseValue -> "$fieldName = ${variable()}"
                        is UpdateClauseColumn -> {
                            var updateColumn = "$fieldName = ${
                                otherColumn.getFieldName(
                                    availableColumns,
                                    tables.dbType
                                )
                            }"
                            if (this.increment != null) {
                                if (this.increment!! > 0) {
                                    updateColumn = updateColumn + "+" + this.increment
                                } else {
                                    updateColumn += this.increment
                                }
                            }
                            updateColumn
                        }
                    }
                }
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
            val rootJoinClauses = (fromClauses[0] as FromClauseTable<*>).joinClauses
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
