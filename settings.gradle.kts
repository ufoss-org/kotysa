pluginManagement {
    val releasePluginVersion: String by settings
    val koverPluginVersion: String by settings
    plugins {
        id("net.researchgate.release") version releasePluginVersion
        id("org.jetbrains.kotlinx.kover") version koverPluginVersion
    }
}

rootProject.name = "kotysa"

val jpmsAsString: String? = System.getProperty("jpms")
var isJpms: Boolean? = null
if (jpmsAsString != null) {
    isJpms = jpmsAsString.toBoolean()
}
println("isJpms = $isJpms")

// MPP projects
include("kotysa-core")
if (isJpms != true) {
    include("kotysa-sqlite")
}

// JVM only projects
if (isJpms != false) {
    include("kotysa-java-tests")
    include("kotysa-tests")
    include("kotysa-jdbc")
    include("kotysa-r2dbc")
    include("kotysa-spring-jdbc")
    include("kotysa-spring-r2dbc")
    include("kotysa-vertx-sqlclient")
}
