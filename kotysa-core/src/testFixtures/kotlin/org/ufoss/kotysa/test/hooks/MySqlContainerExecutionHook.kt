/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MySQLContainer

class MySqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent("mySqlContainer") {
                    val mysqlContainer = KMySQLContainer()
                            .withDatabaseName("db")
                            .withUsername("mysql")
                            .withPassword("test")
                    mysqlContainer.start()
                    println("KMySQLContainer started")

                    MySqlContainerResource(mysqlContainer)
                }
    }
}

internal class KMySQLContainer : MySQLContainer<KMySQLContainer>("mysql:8.0.23")

class MySqlContainerResource internal constructor(
        private val dbContainer: KMySQLContainer
) : TestContainersCloseableResource {
    companion object {
        const val ID = "MySqlContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("KMySQLContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
