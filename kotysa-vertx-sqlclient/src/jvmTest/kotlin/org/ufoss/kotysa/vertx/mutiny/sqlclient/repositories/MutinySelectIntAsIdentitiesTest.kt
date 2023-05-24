package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.IntAsIdentities
import org.ufoss.kotysa.test.IntEntityAsIdentity

private val intAsIdentityWithNullable = IntEntityAsIdentity(
    org.ufoss.kotysa.test.intAsIdentityWithNullable.intNotNull,
    org.ufoss.kotysa.test.intAsIdentityWithNullable.intNullable,
    1
)

private val intAsIdentityWithoutNullable = IntEntityAsIdentity(
    org.ufoss.kotysa.test.intAsIdentityWithoutNullable.intNotNull,
    org.ufoss.kotysa.test.intAsIdentityWithoutNullable.intNullable,
    2
)

interface MutinySelectIntAsIdentitiesTest<T : IntAsIdentities, U : MutinySelectIntAsIdentitiesRepository<T>> :
    MutinyRepositoryTest<U> {

    @Test
    fun `Verify selectAllByIntNotNull finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNull(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNullNotEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() {
        val seq = sequenceOf(intAsIdentityWithNullable.intNotNull, intAsIdentityWithoutNullable.intNotNull)
        Assertions.assertThat(repository.selectAllByIntNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable, intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNullInf(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByIntNotNullInf(10).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNullInfOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intAsIdentityWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByIntNotNullInfOrEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNullSup(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByIntNotNullSup(12).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNotNullSupOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByIntNotNullSupOrEq(12).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullable(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds nothing`() {
        Assertions.assertThat(repository.selectAllByIntNullableNotEq(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        Assertions.assertThat(repository.selectAllByIntNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullableInf(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByIntNullableInf(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullableInfOrEq(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intAsIdentityWithNullable when equals`() {
        Assertions.assertThat(repository.selectAllByIntNullableInfOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullableSup(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        Assertions.assertThat(repository.selectAllByIntNullableSup(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable`() {
        Assertions.assertThat(repository.selectAllByIntNullableSupOrEq(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intAsIdentityWithoutNullable when equals`() {
        Assertions.assertThat(repository.selectAllByIntNullableSupOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(intAsIdentityWithNullable)
    }
}