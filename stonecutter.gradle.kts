plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "26.1.2-fabric"

stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)

    // Makes version- and loader-specific properties apply from `stonecutter.properties.toml`
    properties {
        tags(version, loader)
    }

    // Adds loader constants for Stonecutter comments (e.g. `//? if fabric {`)
    constants {
        match(loader, "fabric", "neoforge")
    }
}
