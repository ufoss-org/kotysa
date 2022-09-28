package com.sample

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) : CommandLineRunner {

    override fun run(vararg args: String) {
        roleRepository.createTable().block()
        userRepository.createTable().block()

        userRepository.deleteAll().block()
        roleRepository.deleteAll().block()

        roleRepository.insert()
        userRepository.insert()
    }
}
