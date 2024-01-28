/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

public abstract class SqlClientQuery protected constructor() {

    public interface SelectableSingle {
        public infix fun <T : Any> select(column: Column<*, T>): Select
        public infix fun <T : Any> selectCount(column: Column<*, T>?): Select
        public infix fun <T : Any> selectDistinct(column: Column<*, T>): Select
        public infix fun <T : Any> selectMin(column: MinMaxColumn<*, T>): Select
        public infix fun <T : Any> selectMax(column: MinMaxColumn<*, T>): Select
        public infix fun <T : Any> selectAvg(column: NumericColumn<*, T>): Select
        public infix fun <T : Any> selectSum(column: WholeNumberColumn<*, T>): Select

        // Postgresql specific
        public fun selectTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select
    }

    public interface Selectable : SelectableSingle {
        public infix fun <T : Any> select(table: Table<T>): Select

        /**
         * sub-query
         */
        public infix fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): Select
        public infix fun <T : Any> selectCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): SelectCaseWhenExists
    }

    public interface SelectCaseWhenExists {
        public infix fun <T : Any> then(value: T): SelectCaseWhenExistsPart2<T>
    }

    public interface SelectCaseWhenExistsPart2<T : Any> {
        public infix fun `else`(value: T): Select
    }

    public interface SelectableFull : Selectable {
        public infix fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): Fromable
        public infix fun <T : Any> selectStarFromSubQuery(
            dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
        ): From
        public fun selects(): Selectable
    }

    public interface Select {
        public infix fun `as`(alias: String): Fromable
    }

    public interface SelectAndable {
        public infix fun <T : Any> and(table: Table<T>): SelectAndable
        public infix fun <T : Any> and(column: Column<*, T>): SelectAndable
        public infix fun <T : Any> andCount(column: Column<*, T>): SelectAndable
        public infix fun <T : Any> andDistinct(column: Column<*, T>): SelectAndable
        public infix fun <T : Any> andMin(column: MinMaxColumn<*, T>): SelectAndable
        public infix fun <T : Any> andMax(column: MinMaxColumn<*, T>): SelectAndable
        public infix fun <T : Any> andAvg(column: NumericColumn<*, T>): SelectAndable
        public infix fun <T : Any> andSum(column: WholeNumberColumn<*, T>): SelectAndable

        // Postgresql specific
        public fun andTsRankCd(tsvectorColumn: TsvectorColumn<*>, tsquery: Tsquery): Select

        /**
         * sub-query
         */
        public infix fun <T : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): SelectAndable
        public infix fun <T : Any> andCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
        ): AndCaseWhenExists
    }

    public interface AndCaseWhenExists {
        public infix fun <T : Any> then(value: T): AndCaseWhenExistsPart2<T>
    }

    public interface AndCaseWhenExistsPart2<T : Any> {
        public infix fun `else`(value: T): SelectAndable
    }

    public interface Fromable {
        public infix fun <T : Any> from(table: Table<T>): FromTable<T>
        public infix fun <T : Any> from(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>): From

        // Postgresql specific
        public infix fun from(tsquery: Tsquery): From
    }

    public interface From {
        public infix fun `as`(alias: String): Any
    }

    public interface FromTable<T : Any> {
        public infix fun <U : Any> innerJoin(table: Table<U>): Joinable<U, *>
    }

    public interface FromTableSelect<T : Any> : FromTable<T> {
        public infix fun <U : Any> leftJoin(table: Table<U>): Joinable<U, *>
        public infix fun <U : Any> rightJoin(table: Table<U>): Joinable<U, *>
        public infix fun <U : Any> fullJoin(table: Table<U>): Joinable<U, *>
    }

    public interface Joinable<T : Any, U : FromTable<T>> {
        public infix fun on(column: Column<*, *>): Join<T, U>

        public infix fun `as`(alias: String): Joinable<T, U>
    }

    public interface Join<T : Any, U : FromTable<T>> {
        public infix fun eq(column: Column<T, *>): U
    }

    public interface FromAndable : From {
        public infix fun <U : Any> and(table: Table<U>): FromTable<U>
        public infix fun <U : Any> and(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<U>): FromAndable

        // Postgresql specific
        public infix fun and(tsquery: Tsquery): FromAndable
    }

    public interface Update<T : Any, U : Update<T, U, V>, V : UpdateInt<T, U, V>> {
        public infix fun set(stringColumnNotNull: StringColumnNotNull<T>): UpdateOpColumn<T, U, String, StringColumn<*>, V>
        public infix fun set(
            stringColumnNullable: StringColumnNullable<T>
        ): UpdateOpColumn<T, U, String?, StringColumn<*>, V>

        public infix fun set(
            localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>
        ): UpdateOpColumn<T, U, LocalDateTime, LocalDateTimeColumn<*>, V>

        public infix fun set(
            localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>
        ): UpdateOpColumn<T, U, LocalDateTime?, LocalDateTimeColumn<*>, V>

        public infix fun set(
            kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalDateTime, KotlinxLocalDateTimeColumn<*>, V>

        public infix fun set(
            kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalDateTime?, KotlinxLocalDateTimeColumn<*>, V>

        public infix fun set(
            localDateColumnNotNull: LocalDateColumnNotNull<T>
        ): UpdateOpColumn<T, U, LocalDate, LocalDateColumn<*>, V>

        public infix fun set(
            localDateColumnNullable: LocalDateColumnNullable<T>
        ): UpdateOpColumn<T, U, LocalDate?, LocalDateColumn<*>, V>

        public infix fun set(
            kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalDate, KotlinxLocalDateColumn<*>, V>

        public infix fun set(
            kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalDate?, KotlinxLocalDateColumn<*>, V>

        public infix fun set(
            offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>
        ): UpdateOpColumn<T, U, OffsetDateTime, OffsetDateTimeColumn<*>, V>

        public infix fun set(
            offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>
        ): UpdateOpColumn<T, U, OffsetDateTime?, OffsetDateTimeColumn<*>, V>

        public infix fun set(
            localTimeColumnNotNull: LocalTimeColumnNotNull<T>
        ): UpdateOpColumn<T, U, LocalTime, LocalTimeColumn<*>, V>

        public infix fun set(
            localTimeColumnNullable: LocalTimeColumnNullable<T>
        ): UpdateOpColumn<T, U, LocalTime?, LocalTimeColumn<*>, V>

        public infix fun set(
            kotlinxLocalTimeColumnNotNull: KotlinxLocalTimeColumnNotNull<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalTime, KotlinxLocalTimeColumn<*>, V>

        public infix fun set(
            kotlinxLocalTimeColumnNullable: KotlinxLocalTimeColumnNullable<T>
        ): UpdateOpColumn<T, U, kotlinx.datetime.LocalTime?, KotlinxLocalTimeColumn<*>, V>

        public infix fun set(
            booleanColumnNotNull: BooleanColumnNotNull<T>
        ): UpdateOpColumn<T, U, Boolean, BooleanColumnNotNull<*>, V>

        public infix fun set(intColumnNotNull: IntColumnNotNull<T>): UpdateOpIntColumn<T, U, Int, IntColumn<*>, V>
        public infix fun set(intColumnNullable: IntColumnNullable<T>): UpdateOpIntColumn<T, U, Int?, IntColumn<*>, V>
        public infix fun set(longColumnNotNull: LongColumnNotNull<T>): UpdateOpIntColumn<T, U, Long, LongColumn<*>, V>
        public infix fun set(longColumnNullable: LongColumnNullable<T>): UpdateOpIntColumn<T, U, Long?, LongColumn<*>, V>
        public infix fun set(floatColumnNotNull: FloatColumnNotNull<T>): UpdateOpIntColumn<T, U, Float, FloatColumn<*>, V>
        public infix fun set(floatColumnNullable: FloatColumnNullable<T>): UpdateOpIntColumn<T, U, Float?, FloatColumn<*>, V>
        public infix fun set(doubleColumnNotNull: DoubleColumnNotNull<T>): UpdateOpIntColumn<T, U, Double, DoubleColumn<*>, V>
        public infix fun set(doubleColumnNullable: DoubleColumnNullable<T>): UpdateOpIntColumn<T, U, Double?, DoubleColumn<*>, V>
        public infix fun set(bigDecimalColumnNotNull: BigDecimalColumnNotNull<T>): UpdateOpIntColumn<T, U, BigDecimal, BigDecimalColumn<*>, V>
        public infix fun set(bigDecimalColumnNullable: BigDecimalColumnNullable<T>): UpdateOpIntColumn<T, U, BigDecimal?, BigDecimalColumn<*>, V>
        public infix fun set(uuidColumnNotNull: UuidColumnNotNull<T>): UpdateOpColumn<T, U, UUID, UuidColumn<*>, V>
        public infix fun set(uuidColumnNullable: UuidColumnNullable<T>): UpdateOpColumn<T, U, UUID?, UuidColumn<*>, V>
        public infix fun set(
            byteArrayColumnNotNull: ByteArrayColumnNotNull<T>
        ): UpdateOpColumn<T, U, ByteArray, ByteArrayColumn<*>, V>

        public infix fun set(
            byteArrayColumnNullable: ByteArrayColumnNullable<T>
        ): UpdateOpColumn<T, U, ByteArray?, ByteArrayColumn<*>, V>
    }

    public interface UpdateOpColumn<T : Any, U : Update<T, U, X>, V, W : Column<*, *>, X : UpdateInt<T, U, X>> {
        public infix fun eq(value: V): U
        public infix fun eq(otherColumn: W): U
    }

    public interface UpdateInt<T : Any, U : Update<T, U, V>, V : UpdateInt<T, U, V>> : Update<T, U, V> {
        public infix fun plus(increment: Int): U
        public infix fun minus(decrement: Int): U
    }

    public interface UpdateOpIntColumn<T : Any, U : Update<T, U, X>, V, W : Column<*, *>, X : UpdateInt<T, U, X>> {
        public infix fun eq(value: V): U
        public infix fun eq(otherColumn: W): X
    }

    public interface Whereable<T : Where<T>> {
        // Where with column
        public infix fun <U : Any> where(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringNotNull<U, T>
        public infix fun <U : Any> where(stringColumnNullable: StringColumnNullable<U>): WhereOpStringNullable<U, T>
        public infix fun <U : Any> where(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>):
                WhereOpDateNotNull<U, T, LocalDateTime>

        public infix fun <U : Any> where(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>):
                WhereOpDateNullable<U, T, LocalDateTime>

        public infix fun <U : Any> where(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>):
                WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDateTime>

        public infix fun <U : Any> where(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>):
                WhereOpDateNullable<U, T, kotlinx.datetime.LocalDateTime>

        public infix fun <U : Any> where(localDateColumnNotNull: LocalDateColumnNotNull<U>):
                WhereOpDateNotNull<U, T, LocalDate>

        public infix fun <U : Any> where(localDateColumnNullable: LocalDateColumnNullable<U>):
                WhereOpDateNullable<U, T, LocalDate>

        public infix fun <U : Any> where(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>):
                WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDate>

        public infix fun <U : Any> where(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>):
                WhereOpDateNullable<U, T, kotlinx.datetime.LocalDate>

        public infix fun <U : Any> where(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>):
                WhereOpDateNotNull<U, T, OffsetDateTime>

        public infix fun <U : Any> where(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>):
                WhereOpDateNullable<U, T, OffsetDateTime>

        public infix fun <U : Any> where(localTimeColumnNotNull: LocalTimeColumnNotNull<U>):
                WhereOpDateNotNull<U, T, LocalTime>

        public infix fun <U : Any> where(localTimeColumnNullable: LocalTimeColumnNullable<U>):
                WhereOpDateNullable<U, T, LocalTime>

        public infix fun <U : Any> where(kotlinxLocalTimeColumnNotNull: KotlinxLocalTimeColumnNotNull<U>):
                WhereOpDateNotNull<U, T, kotlinx.datetime.LocalTime>

        public infix fun <U : Any> where(kotlinxLocalTimeColumnNullable: KotlinxLocalTimeColumnNullable<U>):
                WhereOpDateNullable<U, T, kotlinx.datetime.LocalTime>

        public infix fun <U : Any> where(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanNotNull<U, T>
        public infix fun <U : Any> where(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntNotNull<U, T>
        public infix fun <U : Any> where(intColumnNullable: IntColumnNullable<U>): WhereOpIntNullable<U, T>
        public infix fun <U : Any> where(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongNotNull<U, T>
        public infix fun <U : Any> where(longColumnNullable: LongColumnNullable<U>): WhereOpLongNullable<U, T>
        public infix fun <U : Any> where(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidNotNull<U, T>
        public infix fun <U : Any> where(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidNullable<U, T>
        public infix fun <U : Any> where(byteArrayColumnNotNull: ByteArrayColumnNotNull<U>): WhereOpByteArrayNotNull<U, T>
        public infix fun <U : Any> where(byteArrayColumnNullable: ByteArrayColumnNullable<U>): WhereOpByteArrayNullable<U, T>
        public infix fun <U : Any> where(floatColumnNotNull: FloatColumnNotNull<U>): WhereOpFloatNotNull<U, T>
        public infix fun <U : Any> where(floatColumnNullable: FloatColumnNullable<U>): WhereOpFloatNullable<U, T>
        public infix fun <U : Any> where(doubleColumnNotNull: DoubleColumnNotNull<U>): WhereOpDoubleNotNull<U, T>
        public infix fun <U : Any> where(doubleColumnNullable: DoubleColumnNullable<U>): WhereOpDoubleNullable<U, T>
        public infix fun <U : Any> where(bigDecimalColumnNotNull: BigDecimalColumnNotNull<U>): WhereOpBigDecimalNotNull<U, T>
        public infix fun <U : Any> where(bigDecimalColumnNullable: BigDecimalColumnNullable<U>): WhereOpBigDecimalNullable<U, T>

        // Postgresql specific
        public infix fun <U : Any> where(tsvectorColumn: TsvectorColumn<U>): WhereOpTsvectorNotNull<T>
        public infix fun where(tsquery: Tsquery): WhereOpTsquery<T>

        public infix fun <U : Any> whereExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>): T

        // Where with alias
        public infix fun where(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T>
        public infix fun where(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T>
        public infix fun where(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T>

        public infix fun where(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T>

        public infix fun where(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T>

        public infix fun where(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T>

        public infix fun where(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T>
        public infix fun where(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T>
        public infix fun where(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T>

        public infix fun where(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T>

        public infix fun where(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T>

        public infix fun where(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T>

        public infix fun where(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T>
        public infix fun where(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T>
        public infix fun where(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T>
        public infix fun where(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T>
        public infix fun where(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T>
        public infix fun where(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T>
        public infix fun where(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T>
        public infix fun where(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T>
        public infix fun where(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T>
        public infix fun where(byteArrayAliasNotNull: QueryAlias<ByteArray>): WhereOpByteArrayNotNull<ByteArray, T>
        public infix fun where(byteArrayAliasNullable: QueryAlias<ByteArray?>): WhereOpByteArrayNullable<ByteArray, T>
        public infix fun where(floatAliasNotNull: QueryAlias<Float>): WhereOpFloatNotNull<Float, T>
        public infix fun where(floatAliasNullable: QueryAlias<Float?>): WhereOpFloatNullable<Float, T>
        public infix fun where(doubleAliasNotNull: QueryAlias<Double>): WhereOpDoubleNotNull<Double, T>
        public infix fun where(doubleAliasNullable: QueryAlias<Double?>): WhereOpDoubleNullable<Double, T>
        public infix fun where(bigDecimalAliasNotNull: QueryAlias<BigDecimal>): WhereOpBigDecimalNotNull<BigDecimal, T>
        public infix fun where(bigDecimalAliasNullable: QueryAlias<BigDecimal?>): WhereOpBigDecimalNullable<BigDecimal, T>
    }

    public interface WhereInOp<T : Any, U : Where<U>, V : Any> {
        public infix fun `in`(values: Collection<V>): U
        public infix fun `in`(values: Sequence<V>): U = this.`in`(values.toSet())
        public infix fun `in`(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>): U
    }

    public interface WhereOp<T : Where<T>, U : Any> {
        public infix fun eq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>): T
        public infix fun notEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>): T
    }

    public interface WhereOpNotNull<T : Any, U : Where<U>, V : Any> : WhereOp<U, V> {
        public infix fun eq(value: V): U
        public infix fun notEq(value: V): U
    }

    public interface WhereOpNullable<T : Any, U : Where<U>, V : Any> : WhereOp<U, V> {
        public infix fun eq(value: V?): U
        public infix fun notEq(value: V?): U
    }

    public interface WhereOpString<T : Any, U : Where<U>> : WhereOp<U, String>, WhereInOp<T, U, String> {
        public infix fun contains(value: String): U
        public infix fun startsWith(value: String): U
        public infix fun endsWith(value: String): U
        public infix fun eq(otherStringColumn: StringColumn<*>): U
        public infix fun notEq(otherStringColumn: StringColumn<*>): U
        public infix fun contains(otherStringColumn: StringColumn<*>): U
        public infix fun startsWith(otherStringColumn: StringColumn<*>): U
        public infix fun endsWith(otherStringColumn: StringColumn<*>): U

        public infix fun contains(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<String>): U
        public infix fun startsWith(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<String>): U
        public infix fun endsWith(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<String>): U
    }

    public interface WhereOpStringNotNull<T : Any, U : Where<U>> :
        WhereOpString<T, U>, WhereOpNotNull<T, U, String>

    public interface WhereOpStringNullable<T : Any, U : Where<U>> :
        WhereOpString<T, U>, WhereOpNullable<T, U, String>

    public interface WhereOpDate<T : Any, U : Where<U>, V : Any> : WhereOp<U, V>, WhereInOp<T, U, V> {
        public infix fun before(value: V): U
        public infix fun after(value: V): U
        public infix fun beforeOrEq(value: V): U
        public infix fun afterOrEq(value: V): U

        public infix fun eq(otherDateColumn: Column<*, V>): U
        public infix fun notEq(otherDateColumn: Column<*, V>): U
        public infix fun before(otherDateColumn: Column<*, V>): U
        public infix fun after(otherDateColumn: Column<*, V>): U
        public infix fun beforeOrEq(otherDateColumn: Column<*, V>): U
        public infix fun afterOrEq(otherDateColumn: Column<*, V>): U

        public infix fun before(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>): U
        public infix fun after(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>): U
        public infix fun beforeOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>): U
        public infix fun afterOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<V>): U
    }

    public interface WhereOpDateNotNull<T : Any, U : Where<U>, V : Any> :
        WhereOpDate<T, U, V>, WhereOpNotNull<T, U, V>

    public interface WhereOpLocalDateTimeNotNull<T : Any, U : Where<U>> : WhereOpDateNotNull<T, U, LocalDateTime>

    public interface WhereOpKotlinxLocalDateTimeNotNull<T : Any, U : Where<U>>
        : WhereOpDateNotNull<T, U, kotlinx.datetime.LocalDateTime>

    public interface WhereOpLocalDateNotNull<T : Any, U : Where<U>> : WhereOpDateNotNull<T, U, LocalDate>

    public interface WhereOpKotlinxLocalDateNotNull<T : Any, U : Where<U>>
        : WhereOpDateNotNull<T, U, kotlinx.datetime.LocalDate>

    public interface WhereOpOffsetDateTimeNotNull<T : Any, U : Where<U>> : WhereOpDateNotNull<T, U, OffsetDateTime>

    public interface WhereOpLocalTimeNotNull<T : Any, U : Where<U>> : WhereOpDateNotNull<T, U, LocalTime>

    public interface WhereOpKotlinxLocalTimeNotNull<T : Any, U : Where<U>>
        : WhereOpDateNotNull<T, U, kotlinx.datetime.LocalTime>

    public interface WhereOpDateNullable<T : Any, U : Where<U>, V : Any> :
        WhereOpDate<T, U, V>, WhereOpNullable<T, U, V>

    public interface WhereOpLocalDateTimeNullable<T : Any, U : Where<U>> : WhereOpDateNullable<T, U, LocalDateTime>

    public interface WhereOpKotlinxLocalDateTimeNullable<T : Any, U : Where<U>>
        : WhereOpDateNullable<T, U, kotlinx.datetime.LocalDateTime>

    public interface WhereOpLocalDateNullable<T : Any, U : Where<U>> : WhereOpDateNullable<T, U, LocalDate>

    public interface WhereOpKotlinxLocalDateNullable<T : Any, U : Where<U>>
        : WhereOpDateNullable<T, U, kotlinx.datetime.LocalDate>

    public interface WhereOpOffsetDateTimeNullable<T : Any, U : Where<U>> : WhereOpDateNullable<T, U, OffsetDateTime>

    public interface WhereOpLocalTimeNullable<T : Any, U : Where<U>> : WhereOpDateNullable<T, U, LocalTime>

    public interface WhereOpKotlinxLocalTimeNullable<T : Any, U : Where<U>>
        : WhereOpDateNullable<T, U, kotlinx.datetime.LocalTime>

    public interface WhereOpBooleanNotNull<T : Any, U : Where<U>> : WhereOp<U, Boolean> {
        public infix fun eq(value: Boolean): U
        public infix fun eq(otherBooleanColumn: BooleanColumnNotNull<*>): U
    }

    public interface WhereOpInt<T : Any, U : Where<U>> : WhereOp<U, Int>, WhereInOp<T, U, Int> {
        public infix fun inf(value: Int): U
        public infix fun sup(value: Int): U
        public infix fun infOrEq(value: Int): U
        public infix fun supOrEq(value: Int): U
        public infix fun eq(otherIntColumn: IntColumn<*>): U
        public infix fun notEq(otherIntColumn: IntColumn<*>): U
        public infix fun inf(otherIntColumn: IntColumn<*>): U
        public infix fun sup(otherIntColumn: IntColumn<*>): U
        public infix fun infOrEq(otherIntColumn: IntColumn<*>): U
        public infix fun supOrEq(otherIntColumn: IntColumn<*>): U

        public infix fun inf(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Int>): U
        public infix fun sup(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Int>): U
        public infix fun infOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Int>): U
        public infix fun supOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Int>): U
    }

    public interface WhereOpIntNotNull<T : Any, U : Where<U>> :
        WhereOpInt<T, U>, WhereOpNotNull<T, U, Int>

    public interface WhereOpIntNullable<T : Any, U : Where<U>> :
        WhereOpInt<T, U>, WhereOpNullable<T, U, Int>

    public interface WhereOpLong<T : Any, U : Where<U>> : WhereOp<U, Long>, WhereInOp<T, U, Long> {
        public infix fun inf(value: Long): U
        public infix fun sup(value: Long): U
        public infix fun infOrEq(value: Long): U
        public infix fun supOrEq(value: Long): U
        public infix fun eq(otherLongColumn: LongColumn<*>): U
        public infix fun notEq(otherLongColumn: LongColumn<*>): U
        public infix fun inf(otherLongColumn: LongColumn<*>): U
        public infix fun sup(otherLongColumn: LongColumn<*>): U
        public infix fun infOrEq(otherLongColumn: LongColumn<*>): U
        public infix fun supOrEq(otherLongColumn: LongColumn<*>): U

        public infix fun inf(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Long>): U
        public infix fun sup(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Long>): U
        public infix fun infOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Long>): U
        public infix fun supOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Long>): U
    }

    public interface WhereOpLongNotNull<T : Any, U : Where<U>> :
        WhereOpLong<T, U>, WhereOpNotNull<T, U, Long>

    public interface WhereOpLongNullable<T : Any, U : Where<U>> :
        WhereOpLong<T, U>, WhereOpNullable<T, U, Long>

    public interface WhereOpFloat<T : Any, U : Where<U>> : WhereOp<U, Float>, WhereInOp<T, U, Float> {
        public infix fun inf(value: Float): U
        public infix fun sup(value: Float): U
        public infix fun infOrEq(value: Float): U
        public infix fun supOrEq(value: Float): U
        public infix fun eq(otherFloatColumn: FloatColumn<*>): U
        public infix fun notEq(otherFloatColumn: FloatColumn<*>): U
        public infix fun inf(otherFloatColumn: FloatColumn<*>): U
        public infix fun sup(otherFloatColumn: FloatColumn<*>): U
        public infix fun infOrEq(otherFloatColumn: FloatColumn<*>): U
        public infix fun supOrEq(otherFloatColumn: FloatColumn<*>): U

        public infix fun inf(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Float>): U
        public infix fun sup(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Float>): U
        public infix fun infOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Float>): U
        public infix fun supOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Float>): U
    }

    public interface WhereOpFloatNotNull<T : Any, U : Where<U>> :
        WhereOpFloat<T, U>, WhereOpNotNull<T, U, Float>

    public interface WhereOpFloatNullable<T : Any, U : Where<U>> :
        WhereOpFloat<T, U>, WhereOpNullable<T, U, Float>

    public interface WhereOpDouble<T : Any, U : Where<U>> : WhereOp<U, Double>, WhereInOp<T, U, Double> {
        public infix fun inf(value: Double): U
        public infix fun sup(value: Double): U
        public infix fun infOrEq(value: Double): U
        public infix fun supOrEq(value: Double): U
        public infix fun eq(otherDoubleColumn: DoubleColumn<*>): U
        public infix fun notEq(otherDoubleColumn: DoubleColumn<*>): U
        public infix fun inf(otherDoubleColumn: DoubleColumn<*>): U
        public infix fun sup(otherDoubleColumn: DoubleColumn<*>): U
        public infix fun infOrEq(otherDoubleColumn: DoubleColumn<*>): U
        public infix fun supOrEq(otherDoubleColumn: DoubleColumn<*>): U

        public infix fun inf(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Double>): U
        public infix fun sup(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Double>): U
        public infix fun infOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Double>): U
        public infix fun supOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<Double>): U
    }

    public interface WhereOpDoubleNotNull<T : Any, U : Where<U>> :
        WhereOpDouble<T, U>, WhereOpNotNull<T, U, Double>

    public interface WhereOpDoubleNullable<T : Any, U : Where<U>> :
        WhereOpDouble<T, U>, WhereOpNullable<T, U, Double>

    public interface WhereOpBigDecimal<T : Any, U : Where<U>> : WhereOp<U, BigDecimal>, WhereInOp<T, U, BigDecimal> {
        public infix fun inf(value: BigDecimal): U
        public infix fun sup(value: BigDecimal): U
        public infix fun infOrEq(value: BigDecimal): U
        public infix fun supOrEq(value: BigDecimal): U
        public infix fun eq(otherBigDecimalColumn: BigDecimalColumn<*>): U
        public infix fun notEq(otherBigDecimalColumn: BigDecimalColumn<*>): U
        public infix fun inf(otherBigDecimalColumn: BigDecimalColumn<*>): U
        public infix fun sup(otherBigDecimalColumn: BigDecimalColumn<*>): U
        public infix fun infOrEq(otherBigDecimalColumn: BigDecimalColumn<*>): U
        public infix fun supOrEq(otherBigDecimalColumn: BigDecimalColumn<*>): U

        public infix fun inf(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<BigDecimal>): U
        public infix fun sup(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<BigDecimal>): U
        public infix fun infOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<BigDecimal>): U
        public infix fun supOrEq(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<BigDecimal>): U
    }

    public interface WhereOpBigDecimalNotNull<T : Any, U : Where<U>> :
        WhereOpBigDecimal<T, U>, WhereOpNotNull<T, U, BigDecimal>

    public interface WhereOpBigDecimalNullable<T : Any, U : Where<U>> :
        WhereOpBigDecimal<T, U>, WhereOpNullable<T, U, BigDecimal>

    public interface WhereOpUuid<T : Any, U : Where<U>> : WhereOp<U, UUID>, WhereInOp<T, U, UUID> {
        public infix fun eq(otherUuidColumn: UuidColumn<*>): U
        public infix fun notEq(otherUuidColumn: UuidColumn<*>): U
    }

    public interface WhereOpUuidNotNull<T : Any, U : Where<U>> :
        WhereOpUuid<T, U>, WhereOpNotNull<T, U, UUID>

    public interface WhereOpUuidNullable<T : Any, U : Where<U>> :
        WhereOpUuid<T, U>, WhereOpNullable<T, U, UUID>

    public interface WhereOpByteArray<T : Any, U : Where<U>> : WhereOp<U, ByteArray>, WhereInOp<T, U, ByteArray> {
        public infix fun eq(otherByteArrayColumn: ByteArrayColumn<*>): U
        public infix fun notEq(otherByteArrayColumn: ByteArrayColumn<*>): U
    }

    public interface WhereOpByteArrayNotNull<T : Any, U : Where<U>> :
        WhereOpByteArray<T, U>, WhereOpNotNull<T, U, ByteArray>

    public interface WhereOpByteArrayNullable<T : Any, U : Where<U>> :
        WhereOpByteArray<T, U>, WhereOpNullable<T, U, ByteArray>

    /**
     * See https://www.postgresql.org/docs/current/functions-textsearch.html#TEXTSEARCH-FUNCTIONS-TABLE
     * and https://www.postgresql.org/docs/current/textsearch-controls.html#TEXTSEARCH-PARSING-QUERIES
     */
    public interface WhereOpTsvectorNotNull<T : Where<T>> {
        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * The words must be combined by valid tsquery operators.
         * ```
         * to_tsquery('english', 'The & Fat & Rats') → 'fat' & 'rat'
         * ```
         */
        public infix fun toTsquery(value: String): T

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Any punctuation in the string is ignored (it does not determine query operators).
         * The resulting query matches documents containing all non-stopwords in the text.
         * ```
         * plainto_tsquery('english', 'The Fat Rats') → 'fat' & 'rat'
         * ```
         */
        public infix fun plaintoTsquery(value: String): T

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Any punctuation in the string is ignored (it does not determine query operators).
         * The resulting query matches phrases containing all non-stopwords in the text.
         * ```
         * phraseto_tsquery('english', 'The Fat Rats') → 'fat' <-> 'rat'
         * phraseto_tsquery('english', 'The Cat and Rats') → 'cat' <2> 'rat'
         * ```
         */
        public infix fun phrasetoTsquery(value: String): T

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Quoted word sequences are converted to phrase tests.
         * The word “or” is understood as producing an OR operator, and a dash produces a NOT operator;
         * other punctuation is ignored. This approximates the behavior of some common web search tools.
         * ```
         * websearch_to_tsquery('english', '"fat rat" or cat dog') → 'fat' <-> 'rat' | 'cat' & 'dog'
         * ```
         */
        public infix fun websearchToTsquery(value: String): T
    }

    /**
     * See https://www.postgresql.org/docs/15/textsearch-controls.html#TEXTSEARCH-RANKING
     */
    public interface WhereOpTsquery<T : Where<T>> {
        public infix fun applyOn(tsvectorColumn: TsvectorColumn<*>): T
    }

    public interface Where<T : Where<T>>

    public interface Andable<T : Where<T>> {
        public infix fun <U : Any> and(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringNotNull<U, T>
        public infix fun <U : Any> and(stringColumnNullable: StringColumnNullable<U>): WhereOpStringNullable<U, T>
        public infix fun <U : Any> and(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalDateTime>
        public infix fun <U : Any> and(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, LocalDateTime>
        public infix fun <U : Any> and(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> and(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> and(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalDate>
        public infix fun <U : Any> and(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateNullable<U, T, LocalDate>
        public infix fun <U : Any> and(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> and(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> and(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, OffsetDateTime>
        public infix fun <U : Any> and(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, OffsetDateTime>
        public infix fun <U : Any> and(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalTime>
        public infix fun <U : Any> and(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateNullable<U, T, LocalTime>
        public infix fun <U : Any> and(kotlinxLocalTimeColumnNotNull: KotlinxLocalTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalTime>
        public infix fun <U : Any> and(kotlinxLocalTimeColumnNullable: KotlinxLocalTimeColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalTime>
        public infix fun <U : Any> and(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanNotNull<U, T>
        public infix fun <U : Any> and(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntNotNull<U, T>
        public infix fun <U : Any> and(intColumnNullable: IntColumnNullable<U>): WhereOpIntNullable<U, T>
        public infix fun <U : Any> and(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongNotNull<U, T>
        public infix fun <U : Any> and(longColumnNullable: LongColumnNullable<U>): WhereOpLongNullable<U, T>
        public infix fun <U : Any> and(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidNotNull<U, T>
        public infix fun <U : Any> and(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidNullable<U, T>
        public infix fun <U : Any> and(byteArrayColumnNotNull: ByteArrayColumnNotNull<U>): WhereOpByteArrayNotNull<U, T>
        public infix fun <U : Any> and(byteArrayColumnNullable: ByteArrayColumnNullable<U>)
                : WhereOpByteArrayNullable<U, T>

        public infix fun <U : Any> and(floatColumnNotNull: FloatColumnNotNull<U>): WhereOpFloatNotNull<U, T>
        public infix fun <U : Any> and(floatColumnNullable: FloatColumnNullable<U>): WhereOpFloatNullable<U, T>
        public infix fun <U : Any> and(doubleColumnNotNull: DoubleColumnNotNull<U>): WhereOpDoubleNotNull<U, T>
        public infix fun <U : Any> and(doubleColumnNullable: DoubleColumnNullable<U>): WhereOpDoubleNullable<U, T>
        public infix fun <U : Any> and(bigDecimalColumnNotNull: BigDecimalColumnNotNull<U>): WhereOpBigDecimalNotNull<U, T>
        public infix fun <U : Any> and(bigDecimalColumnNullable: BigDecimalColumnNullable<U>): WhereOpBigDecimalNullable<U, T>
        public infix fun <U : Any> and(tsvectorColumn: TsvectorColumn<U>): WhereOpTsvectorNotNull<T>
        public infix fun and(tsquery: Tsquery): WhereOpTsquery<T>
        public infix fun <U : Any> andExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>): T

        // And with alias
        public infix fun and(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T>
        public infix fun and(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T>
        public infix fun and(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T>

        public infix fun and(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T>

        public infix fun and(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T>

        public infix fun and(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T>

        public infix fun and(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T>
        public infix fun and(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T>
        public infix fun and(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T>

        public infix fun and(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T>

        public infix fun and(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T>

        public infix fun and(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T>

        public infix fun and(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T>
        public infix fun and(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T>
        public infix fun and(kotlinxLocalTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalTime>):
                WhereOpKotlinxLocalTimeNotNull<kotlinx.datetime.LocalTime, T>

        public infix fun and(kotlinxLocalTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalTime?>):
                WhereOpKotlinxLocalTimeNullable<kotlinx.datetime.LocalTime, T>

        public infix fun and(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T>
        public infix fun and(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T>
        public infix fun and(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T>
        public infix fun and(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T>
        public infix fun and(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T>
        public infix fun and(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T>
        public infix fun and(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T>
        public infix fun and(byteArrayAliasNotNull: QueryAlias<ByteArray>): WhereOpByteArrayNotNull<ByteArray, T>
        public infix fun and(byteArrayAliasNullable: QueryAlias<ByteArray?>): WhereOpByteArrayNullable<ByteArray, T>
        public infix fun and(floatAliasNotNull: QueryAlias<Float>): WhereOpFloatNotNull<Float, T>
        public infix fun and(floatAliasNullable: QueryAlias<Float?>): WhereOpFloatNullable<Float, T>
        public infix fun and(doubleAliasNotNull: QueryAlias<Double>): WhereOpDoubleNotNull<Double, T>
        public infix fun and(doubleAliasNullable: QueryAlias<Double?>): WhereOpDoubleNullable<Double, T>
        public infix fun and(bigDecimalAliasNotNull: QueryAlias<BigDecimal>): WhereOpBigDecimalNotNull<BigDecimal, T>
        public infix fun and(bigDecimalAliasNullable: QueryAlias<BigDecimal?>): WhereOpBigDecimalNullable<BigDecimal, T>
    }

    public interface Orable<T : Where<T>> {
        public infix fun <U : Any> or(stringColumnNotNull: StringColumnNotNull<U>): WhereOpStringNotNull<U, T>
        public infix fun <U : Any> or(stringColumnNullable: StringColumnNullable<U>): WhereOpStringNullable<U, T>
        public infix fun <U : Any> or(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalDateTime>
        public infix fun <U : Any> or(localDateTimeColumnNullable: LocalDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, LocalDateTime>
        public infix fun <U : Any> or(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> or(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalDateTime>
        public infix fun <U : Any> or(localDateColumnNotNull: LocalDateColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalDate>
        public infix fun <U : Any> or(localDateColumnNullable: LocalDateColumnNullable<U>): WhereOpDateNullable<U, T, LocalDate>
        public infix fun <U : Any> or(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> or(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalDate>
        public infix fun <U : Any> or(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, OffsetDateTime>
        public infix fun <U : Any> or(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<U>): WhereOpDateNullable<U, T, OffsetDateTime>
        public infix fun <U : Any> or(localTimeColumnNotNull: LocalTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, LocalTime>
        public infix fun <U : Any> or(localTimeColumnNullable: LocalTimeColumnNullable<U>): WhereOpDateNullable<U, T, LocalTime>
        public infix fun <U : Any> or(kotlinxLocalTimeColumnNotNull: KotlinxLocalTimeColumnNotNull<U>): WhereOpDateNotNull<U, T, kotlinx.datetime.LocalTime>
        public infix fun <U : Any> or(kotlinxLocalTimeColumnNullable: KotlinxLocalTimeColumnNullable<U>): WhereOpDateNullable<U, T, kotlinx.datetime.LocalTime>
        public infix fun <U : Any> or(booleanColumnNotNull: BooleanColumnNotNull<U>): WhereOpBooleanNotNull<U, T>
        public infix fun <U : Any> or(intColumnNotNull: IntColumnNotNull<U>): WhereOpIntNotNull<U, T>
        public infix fun <U : Any> or(intColumnNullable: IntColumnNullable<U>): WhereOpIntNullable<U, T>
        public infix fun <U : Any> or(longColumnNotNull: LongColumnNotNull<U>): WhereOpLongNotNull<U, T>
        public infix fun <U : Any> or(longColumnNullable: LongColumnNullable<U>): WhereOpLongNullable<U, T>
        public infix fun <U : Any> or(uuidColumnNotNull: UuidColumnNotNull<U>): WhereOpUuidNotNull<U, T>
        public infix fun <U : Any> or(uuidColumnNullable: UuidColumnNullable<U>): WhereOpUuidNullable<U, T>
        public infix fun <U : Any> or(byteArrayColumnNotNull: ByteArrayColumnNotNull<U>): WhereOpByteArrayNotNull<U, T>
        public infix fun <U : Any> or(byteArrayColumnNullable: ByteArrayColumnNullable<U>): WhereOpByteArrayNullable<U, T>
        public infix fun <U : Any> or(floatColumnNotNull: FloatColumnNotNull<U>): WhereOpFloatNotNull<U, T>
        public infix fun <U : Any> or(floatColumnNullable: FloatColumnNullable<U>): WhereOpFloatNullable<U, T>
        public infix fun <U : Any> or(doubleColumnNotNull: DoubleColumnNotNull<U>): WhereOpDoubleNotNull<U, T>
        public infix fun <U : Any> or(doubleColumnNullable: DoubleColumnNullable<U>): WhereOpDoubleNullable<U, T>
        public infix fun <U : Any> or(bigDecimalColumnNotNull: BigDecimalColumnNotNull<U>): WhereOpBigDecimalNotNull<U, T>
        public infix fun <U : Any> or(bigDecimalColumnNullable: BigDecimalColumnNullable<U>): WhereOpBigDecimalNullable<U, T>
        public infix fun <U : Any> or(tsvectorColumn: TsvectorColumn<U>): WhereOpTsvectorNotNull<T>
        public infix fun or(tsquery: Tsquery): WhereOpTsquery<T>
        public infix fun <U : Any> orExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>): T

        // Or with alias
        public infix fun or(stringAliasNotNull: QueryAlias<String>): WhereOpStringNotNull<String, T>
        public infix fun or(stringAliasNullable: QueryAlias<String?>): WhereOpStringNullable<String, T>
        public infix fun or(localDateTimeAliasNotNull: QueryAlias<LocalDateTime>):
                WhereOpLocalDateTimeNotNull<LocalDateTime, T>

        public infix fun or(localDateTimeAliasNullable: QueryAlias<LocalDateTime?>):
                WhereOpLocalDateTimeNullable<LocalDateTime, T>

        public infix fun or(kotlinxLocalDateTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalDateTime>):
                WhereOpKotlinxLocalDateTimeNotNull<kotlinx.datetime.LocalDateTime, T>

        public infix fun or(kotlinxLocalDateTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalDateTime?>):
                WhereOpKotlinxLocalDateTimeNullable<kotlinx.datetime.LocalDateTime, T>

        public infix fun or(localDateAliasNotNull: QueryAlias<LocalDate>): WhereOpLocalDateNotNull<LocalDate, T>
        public infix fun or(localDateAliasNullable: QueryAlias<LocalDate?>): WhereOpLocalDateNullable<LocalDate, T>
        public infix fun or(kotlinxLocalDateAliasNotNull: QueryAlias<kotlinx.datetime.LocalDate>):
                WhereOpKotlinxLocalDateNotNull<kotlinx.datetime.LocalDate, T>

        public infix fun or(kotlinxLocalDateAliasNullable: QueryAlias<kotlinx.datetime.LocalDate?>):
                WhereOpKotlinxLocalDateNullable<kotlinx.datetime.LocalDate, T>

        public infix fun or(offsetDateTimeAliasNotNull: QueryAlias<OffsetDateTime>):
                WhereOpOffsetDateTimeNotNull<OffsetDateTime, T>

        public infix fun or(offsetDateTimeAliasNullable: QueryAlias<OffsetDateTime?>):
                WhereOpOffsetDateTimeNullable<OffsetDateTime, T>

        public infix fun or(localTimeAliasNotNull: QueryAlias<LocalTime>): WhereOpLocalTimeNotNull<LocalTime, T>
        public infix fun or(localTimeAliasNullable: QueryAlias<LocalTime?>): WhereOpLocalTimeNullable<LocalTime, T>
        public infix fun or(kotlinxLocalTimeAliasNotNull: QueryAlias<kotlinx.datetime.LocalTime>):
                WhereOpKotlinxLocalTimeNotNull<kotlinx.datetime.LocalTime, T>

        public infix fun or(kotlinxLocalTimeAliasNullable: QueryAlias<kotlinx.datetime.LocalTime?>):
                WhereOpKotlinxLocalTimeNullable<kotlinx.datetime.LocalTime, T>

        public infix fun or(booleanAliasNotNull: QueryAlias<Boolean>): WhereOpBooleanNotNull<Boolean, T>
        public infix fun or(intAliasNotNull: QueryAlias<Int>): WhereOpIntNotNull<Int, T>
        public infix fun or(intAliasNullable: QueryAlias<Int?>): WhereOpIntNullable<Int, T>
        public infix fun or(longAliasNotNull: QueryAlias<Long>): WhereOpLongNotNull<Long, T>
        public infix fun or(longAliasNullable: QueryAlias<Long?>): WhereOpLongNullable<Long, T>
        public infix fun or(uuidAliasNotNull: QueryAlias<UUID>): WhereOpUuidNotNull<UUID, T>
        public infix fun or(uuidAliasNullable: QueryAlias<UUID?>): WhereOpUuidNullable<UUID, T>
        public infix fun or(byteArrayAliasNotNull: QueryAlias<ByteArray>): WhereOpByteArrayNotNull<ByteArray, T>
        public infix fun or(byteArrayAliasNullable: QueryAlias<ByteArray?>): WhereOpByteArrayNullable<ByteArray, T>
        public infix fun or(floatAliasNotNull: QueryAlias<Float>): WhereOpFloatNotNull<Float, T>
        public infix fun or(floatAliasNullable: QueryAlias<Float?>): WhereOpFloatNullable<Float, T>
        public infix fun or(doubleAliasNotNull: QueryAlias<Double>): WhereOpDoubleNotNull<Double, T>
        public infix fun or(doubleAliasNullable: QueryAlias<Double?>): WhereOpDoubleNullable<Double, T>
        public infix fun or(bigDecimalAliasNotNull: QueryAlias<BigDecimal>): WhereOpBigDecimalNotNull<BigDecimal, T>
        public infix fun or(bigDecimalAliasNullable: QueryAlias<BigDecimal?>): WhereOpBigDecimalNullable<BigDecimal, T>
    }

    public interface LimitOffset<T : LimitOffset<T>> {
        public infix fun limit(limit: Long): T
        public infix fun offset(offset: Long): T
    }

    public interface GroupableBy<T : GroupBy<T>> {
        public infix fun groupBy(column: Column<*, *>): T
        public infix fun groupBy(alias: QueryAlias<*>): T
    }

    public interface GroupBy<T : GroupBy<T>>

    public interface GroupByAndable<T : GroupBy<T>> : GroupBy<T> {
        public infix fun and(column: Column<*, *>): T
        public infix fun and(alias: QueryAlias<*>): T

        // todo HAVING https://www.dofactory.com/sql/having
    }

    public interface OrderableBy<T : OrderBy<T>> {
        public infix fun orderByAsc(column: Column<*, *>): T
        public infix fun orderByDesc(column: Column<*, *>): T

        // With alias
        public infix fun orderByAsc(alias: QueryAlias<*>): T
        public infix fun orderByDesc(alias: QueryAlias<*>): T

        public infix fun <U : Any> orderByAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): OrderByCaseWhenExists<U, T>

        public infix fun <U : Any> orderByDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): OrderByCaseWhenExists<U, T>
    }

    public interface OrderByCaseWhenExists<T : Any, U : OrderBy<U>> {
        public infix fun <V : Any> then(value: V): OrderByCaseWhenExistsPart2<T, V, U>
    }

    public interface OrderByCaseWhenExistsPart2<T : Any, U : Any, V : OrderBy<V>> {
        public infix fun `else`(value: U): V
    }

    public interface OrderBy<T : OrderBy<T>>

    public interface OrderByAndable<T : OrderByAndable<T>> : OrderBy<T> {
        public infix fun andAsc(column: Column<*, *>): T
        public infix fun andDesc(column: Column<*, *>): T

        // With alias
        public infix fun andAsc(alias: QueryAlias<*>): T
        public infix fun andDesc(alias: QueryAlias<*>): T

        public infix fun <U : Any> andAscCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): OrderByCaseWhenExists<U, T>

        public infix fun <U : Any> andDescCaseWhenExists(
            dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>
        ): OrderByCaseWhenExists<U, T>
    }

    // PostgreSQL specific
    public interface ToTsquery {
        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * The words must be combined by valid tsquery operators.
         * ```
         * to_tsquery('english', 'The & Fat & Rats') → 'fat' & 'rat'
         * ```
         */
        public infix fun toTsquery(value: String): ToTsqueryPart2 = ToTsqueryPart2(value, Operation.TO_TSQUERY)

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Any punctuation in the string is ignored (it does not determine query operators).
         * The resulting query matches documents containing all non-stopwords in the text.
         * ```
         * plainto_tsquery('english', 'The Fat Rats') → 'fat' & 'rat'
         * ```
         */
        public infix fun plaintoTsquery(value: String): ToTsqueryPart2 =
            ToTsqueryPart2(value, Operation.PLAINTO_TSQUERY)

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Any punctuation in the string is ignored (it does not determine query operators).
         * The resulting query matches phrases containing all non-stopwords in the text.
         * ```
         * phraseto_tsquery('english', 'The Fat Rats') → 'fat' <-> 'rat'
         * phraseto_tsquery('english', 'The Cat and Rats') → 'cat' <2> 'rat'
         * ```
         */
        public infix fun phrasetoTsquery(value: String): ToTsqueryPart2 =
            ToTsqueryPart2(value, Operation.PHRASETO_TSQUERY)

        /**
         * Converts text to a tsquery, normalizing words according to the specified or default configuration.
         * Quoted word sequences are converted to phrase tests.
         * The word “or” is understood as producing an OR operator, and a dash produces a NOT operator;
         * other punctuation is ignored. This approximates the behavior of some common web search tools.
         * ```
         * websearch_to_tsquery('english', '"fat rat" or cat dog') → 'fat' <-> 'rat' | 'cat' & 'dog'
         * ```
         */
        public infix fun websearchToTsquery(value: String): ToTsqueryPart2 =
            ToTsqueryPart2(value, Operation.WEBSEARCH_TO_TSQUERY)
    }

    public class ToTsqueryPart2 internal constructor(
        private val value: String,
        private val operation: Operation,
    ) {
        public infix fun `as`(alias: String): Tsquery = Tsquery(value, operation, alias)
    }
}
