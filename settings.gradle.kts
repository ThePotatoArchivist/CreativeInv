pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.9.6"
	// Lets Gradle auto-provision the JDK required by gradle/gradle-daemon-jvm.properties
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
	create(rootProject) {
		/**
		 * Creates version nodes for multiple loaders, named `versions/{project}-{loader}`,
		 * each using the loader-specific `build.{loader}.gradle.kts` build script.
		 */
		fun match(project: String, vararg loaders: String, version: String = project) {
			for (loader in loaders) version("$project-$loader", version).buildscript("build.$loader.gradle.kts")
		}

		match("26.1.2", "fabric", "neoforge")
		vcsVersion = "26.1.2-fabric"
	}
}
