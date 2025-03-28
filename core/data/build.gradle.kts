import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "data"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(project(":core:database"))
            implementation(project(":core:model"))
            implementation(compose.runtime)
            implementation(compose.foundation)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.kotlinx.datetime)

            api(libs.koin.core)
            implementation(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.yanfalcao.data"
    compileSdk = 34

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    ksp(libs.androidx.room.compiler)
}