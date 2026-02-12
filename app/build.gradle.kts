plugins {
	id("echo.spring-boot-app")
	alias(libs.plugins.kotlin.jpa)
}

group = "com.llimapons"
version = "0.0.1-SNAPSHOT"
description = "Echo Backend"

dependencies {

	implementation(projects.chat)
	implementation(projects.common)
	implementation(projects.notification)
	implementation(projects.user)

	implementation(libs.kotlin.reflect)
	implementation(libs.spring.boot.starter.security)

	implementation(libs.spring.boot.starter.data.jpa)
	runtimeOnly(libs.postgresql)
}