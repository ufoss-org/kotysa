/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.TinyTexts
import org.ufoss.kotysa.test.stringAsTinyTextNotNull
import org.ufoss.kotysa.test.stringAsTinyTextNullable
import org.ufoss.kotysa.transaction.Transaction

interface ReactorSelectStringAsTinyTextTest<T : TinyTexts, U : ReactorSelectStringAsTinyTextRepository<T>,
        V : Transaction> : ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsTinyTextNotNull.stringNotNull).block())
            .isEqualTo(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstOrNullByStringNotNull("Unknown").block())
            .isNull()
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown, throws NoResultException`() {
        assertThatThrownBy { repository.selectFirstByStringNotNull("Unknown").block() }
            .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTinyTextNotNull.stringNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsTinyTextNotNull.stringNotNull, stringAsTinyTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTinyTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTinyTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTinyTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsTinyTextNotNull.stringNullable).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTinyTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTinyTextNotNull.stringNullable).toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTinyTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTinyTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTinyTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toIterable())
            .hasSize(0)
    }
}
