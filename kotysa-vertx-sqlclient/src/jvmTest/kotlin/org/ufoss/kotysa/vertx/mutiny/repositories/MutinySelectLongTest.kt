package org.ufoss.kotysa.vertx.mutiny.repositories

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Longs

interface MutinySelectLongTest<T : Longs, U : MutinySelectLongRepository<T>> : MutinyRepositoryTest<U> {

    private val longWithNullable get() = repository.generatedLongWithNullable
    private val longWithoutNullable get() = repository.generatedLongWithoutNullable

    @Test
    fun `Verify selectAllByLongNotNull finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNull(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds longWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNullNotEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        Assertions.assertThat(repository.selectAllByLongNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNullInf(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLongNotNullInf(10).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNullInfOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds longWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLongNotNullInfOrEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds longWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNullSup(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLongNotNullSup(12).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLongNotNullSupOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds longWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLongNotNullSupOrEq(12).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullable(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds longWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByLongNullableNotEq(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullableInf(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLongNullableInf(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullableInfOrEq(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds longWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLongNullableInfOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullableSup(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByLongNullableSup(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable`() {
        Assertions.assertThat(repository.selectAllByLongNullableSupOrEq(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds longWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByLongNullableSupOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }
}