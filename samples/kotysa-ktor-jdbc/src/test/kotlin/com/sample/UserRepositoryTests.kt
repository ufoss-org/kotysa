package com.sample

import io.ktor.application.*
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

class UserRepositoryTests {

    @Test
    fun count() = kotysaTest {
        val userRepository by closestDI().instance<UserRepository>()
        assertThat(userRepository.count())
            .isEqualTo(2)
    }

    @Test
    fun selectWithJoin() = kotysaTest {
        val userRepository by closestDI().instance<UserRepository>()
        assertThat(userRepository.selectWithJoin())
            .hasSize(2)
    }

    private fun kotysaTest(test: Application.() -> Unit) {
        withTestApplication({
            configureApp("test")
            test()
        }, {})
    }
}
