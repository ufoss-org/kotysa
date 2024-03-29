/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.hooks

import com.github.dockerjava.api.command.InspectContainerResponse
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.testcontainers.oracle.OracleContainer

public class OracleContainerExecutionHook : ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext?): Boolean {
        return TestContainersCloseableResource::class.java == parameterContext.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext.root.getStore(ExtensionContext.Namespace.GLOBAL)
            .getOrComputeIfAbsent("oracleContainer") {
                val oracleContainer = OracleContainer("gvenzl/oracle-free:23.3-slim-faststart")
                    .withDatabaseName("db")
                oracleContainer.start()
                println("OracleContainer started")

                OracleContainerResource(oracleContainer)
            }
    }
}

public class OracleContainerResource internal constructor(
    private val dbContainer: OracleContainer
) : TestContainersCloseableResource {
    public companion object {
        public const val ID: String = "OracleContainerResource"
    }

    override fun close() {
        dbContainer.stop()
        println("OracleContainer stopped")
    }

    override fun getExposedPorts(): MutableList<Int> = dbContainer.exposedPorts

    override fun getContainerInfo(): InspectContainerResponse = dbContainer.containerInfo
}
