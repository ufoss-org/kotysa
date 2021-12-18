package com.sample

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) : CommandLineRunner {

    override fun run(vararg args: String) {
        userRepository.init()
        roleRepository.init()
    }
}
