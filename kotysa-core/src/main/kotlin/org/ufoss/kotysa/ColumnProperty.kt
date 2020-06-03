/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface ColumnProperty<T : Any>


public interface NotNullColumnProperty<T : Any> : ColumnProperty<T>


public interface NullableColumnProperty<T : Any> : ColumnProperty<T>


public abstract class AbstractColumnProperty<T : Any> : ColumnProperty<T> {
    internal abstract val getter: (T) -> Any?
}

// String

public abstract class StringColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullStringColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> String
) : StringColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableStringColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> String?
) : StringColumnProperty<T>(), NullableColumnProperty<T>

// LocalDateTime

public abstract class LocalDateTimeColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDateTime
) : LocalDateTimeColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDateTime?
) : LocalDateTimeColumnProperty<T>(), NullableColumnProperty<T>

// LocalDate

public abstract class LocalDateColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullLocalDateColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDate
) : LocalDateColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableLocalDateColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDate?
) : LocalDateColumnProperty<T>(), NullableColumnProperty<T>

// OffsetDateTime

public abstract class OffsetDateTimeColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullOffsetDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> OffsetDateTime
) : OffsetDateTimeColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableOffsetDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> OffsetDateTime?
) : OffsetDateTimeColumnProperty<T>(), NullableColumnProperty<T>

// LocalTime

public abstract class LocalTimeColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullLocalTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalTime
) : LocalTimeColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableLocalTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalTime?
) : LocalTimeColumnProperty<T>(), NullableColumnProperty<T>

// Boolean

public class NotNullBooleanColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Boolean
) : AbstractColumnProperty<T>(), NotNullColumnProperty<T>

// UUID

public abstract class UuidColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullUuidColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> UUID
) : UuidColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableUuidColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> UUID?
) : UuidColumnProperty<T>(), NullableColumnProperty<T>

// Int

public abstract class IntColumnProperty<T : Any> : AbstractColumnProperty<T>()


public class NotNullIntColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Int
) : IntColumnProperty<T>(), NotNullColumnProperty<T>


public class NullableIntColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Int?
) : IntColumnProperty<T>(), NullableColumnProperty<T>
