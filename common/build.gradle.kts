plugins {
    id("java-library")
    id("echo.kotlin-common")
}

group = "com.llimapons"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kotlin.reflect)
    api(libs.jackson.module.kotlin)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}