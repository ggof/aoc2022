import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.20"
}

allprojects {
	group = "xyz.ggof.aoc2022"
	version = "1.0-SNAPSHOT"

	apply(plugin = "org.jetbrains.kotlin.jvm")

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation(platform("io.arrow-kt:arrow-stack:1.1.2"))
		implementation("io.arrow-kt:arrow-core")
		implementation("io.arrow-kt:arrow-optics")

		testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
		testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	}

	tasks.test {
		useJUnitPlatform()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "17"
		kotlinOptions.useK2 = true
	}
}