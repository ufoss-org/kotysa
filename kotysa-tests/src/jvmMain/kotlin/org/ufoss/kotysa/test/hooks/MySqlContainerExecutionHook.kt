/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.containers.MySQLContainer

public class MySqlContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
            .getOrComputeIfAbsent("mySqlContainer") {
                val mysqlContainer = MySQLContainer("mysql:8.0.31-debian")
                    .withDatabaseName("db")
                    .withUsername("mysql")
                    .withPassword("test")
                    .withCommand("mysqld --default-authentication-plugin=mysql_native_password")
                mysqlContainer.start()
                println("MySQLContainer started")

                MySqlContainerResource(mysqlContainer)
            }
    }
}

public class MySqlContainerResource internal constructor(
    private val dbContainer: MySQLContainer<*>
) : TestContainersCloseableResource {
    public companion object {
        public const val ID: String = "MySqlContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("MySQLContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
