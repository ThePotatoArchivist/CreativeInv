plugins {
	// Since Minecraft 26.1 the game is unobfuscated: use the non-remapping loom variant
	id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT"
}

val javaVersion = JavaVersion.VERSION_25
val fabricApiVersion: String = sc.properties["deps.fabric_api"]
val mcVersionRangeForFabric: String = sc.properties["mod.mc_compat"]

version = "${property("mod.version")}+${sc.current.version}"
group = property("mod.group").toString()

base {
	archivesName = "${property("mod.id")}-fabric"
}

dependencies {
	minecraft("com.mojang:minecraft:${sc.current.version}")
	implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	implementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
}

tasks {
	processResources {
		inputs.property("java", javaVersion.majorVersion)
		inputs.property("minecraftVersionRange", mcVersionRangeForFabric)
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand(mapOf(
				"java" to inputs.properties["java"],
				"minecraftVersionRange" to inputs.properties["minecraftVersionRange"],
				"version" to inputs.properties["version"],
			))
		}

		exclude("META-INF/neoforge.mods.toml")
	}

	jar {
		inputs.property("archivesName", project.base.archivesName)

		from(rootProject.file("LICENSE")) {
			rename { "${it}_${inputs.properties["archivesName"]}"}
		}
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(javaVersion.majorVersion.toInt())
}

java {
	sourceCompatibility = javaVersion
	targetCompatibility = javaVersion
}

loom {
	runConfigs.all {
		generateRunConfig = true // Run configurations are not created for subprojects by default
		runDirectory = rootProject.file("run") // Use a shared run folder and create separate worlds
	}
}
