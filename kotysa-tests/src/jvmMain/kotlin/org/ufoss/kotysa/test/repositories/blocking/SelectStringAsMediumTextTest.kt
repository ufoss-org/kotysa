/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.test.MediumTexts
import org.ufoss.kotysa.test.stringAsMediumTextNotNull
import org.ufoss.kotysa.test.stringAsMediumTextNullable
import org.ufoss.kotysa.transaction.Transaction

interface SelectStringAsMediumTextTest<T : MediumTexts, U : SelectStringAsMediumTextRepository<T>, V : Transaction> :
    RepositoryTest<U, V> {

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsMediumTextNotNull.stringNotNull))
            .isEqualTo(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstOrNullByStringNotNull("Unknown"))
            .isNull()
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown, throws NoResultException`() {
        Assertions.assertThatThrownBy { repository.selectFirstByStringNotNull("Unknown") }
            .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsMediumTextNotNull.stringNotNull))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown"))
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull, stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsMediumTextNotNull.stringNotNull, stringAsMediumTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq))
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull, stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsMediumTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsMediumTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsMediumTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsMediumTextNotNull.stringNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsMediumTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsMediumTextNotNull.stringNullable))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsMediumTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsMediumTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsMediumTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de"))
            .hasSize(0)
    }
}
