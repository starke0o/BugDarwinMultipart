plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.aakira:napier:2.6.1")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

                implementation(platform("io.ktor:ktor-bom:2.3.3"))
                implementation("io.ktor:ktor-client-core")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin")
            }
        }
    }
}

android {
    namespace = "com.example.bugdarwinmultipart"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}