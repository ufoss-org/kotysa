package com.sample

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) : CommandLineRunner {

    override fun run(vararg args: String) {
        roleRepository.createTable()
        userRepository.createTable()

        userRepository.deleteAll()
        roleRepository.deleteAll()
        
        roleRepository.insert()
        userRepository.insert()
    }
}
