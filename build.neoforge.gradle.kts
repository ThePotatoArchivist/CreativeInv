plugins {
	id("net.neoforged.moddev") version "2.0.141"
}

val javaVersion = JavaVersion.VERSION_25
val mcVersionRangeForNeoForge: String = sc.properties["mod.mc_compat"]

version = "${property("mod.version")}+${sc.current.version}"
group = property("mod.group").toString()

base {
	archivesName = "${property("mod.id")}-neoforge"
}

neoForge {
	version = sc.properties["deps.neo_loader"]

	mods {
		register("subsidy") {
			sourceSet(sourceSets.main.get())
		}
	}

	runs {
		register("client") {
			client()
			gameDirectory = rootProject.file("run")
		}
		register("server") {
			server()
			gameDirectory = rootProject.file("run")
		}
	}
}

tasks {
	processResources {
		inputs.property("minecraftVersionRange", mcVersionRangeForNeoForge)
		inputs.property("version", project.version)

		filesMatching("META-INF/neoforge.mods.toml") {
			expand(mapOf(
				"minecraftVersionRange" to inputs.properties["minecraftVersionRange"],
				"version" to inputs.properties["version"],
			))
		}

		exclude("fabric.mod.json")
	}

	named("createMinecraftArtifacts") {
		dependsOn("stonecutterGenerate")
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

	toolchain {
		languageVersion = JavaLanguageVersion.of(javaVersion.majorVersion)
	}
}
