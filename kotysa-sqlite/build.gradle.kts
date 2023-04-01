val jpmsAsString: String? = System.getProperty("jpms")
var isJpms: Boolean? = null
if (jpmsAsString != null) {
    isJpms = jpmsAsString.toBoolean()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":kotysa-core"))

                compileOnly("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
            }
        }
    }
    
    if (isJpms != false) {
        sourceSets["commonTest"].dependencies {
            implementation(project(":kotysa-tests"))
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
        }
    }
}
