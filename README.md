# Subsidy

A Minecraft mod that gives selected players access to the creative
inventory while they stay in survival mode — health, hunger and no
flight included.

## About this fork

This is a fork of [ThePotatoArchivist/Subsidy](https://github.com/ThePotatoArchivist/Subsidy)
(originally published on [Modrinth](https://modrinth.com/mod/subsidy)),
updated from the Minecraft 26.1 snapshots to the 26.1.2 release and
extended with **NeoForge support** next to the original Fabric version.
Both jars are built from one codebase and published through GitHub
Releases.

This port is dedicated to my 6-year-old, who loves to fight mobs but
also loves the freedom of the creative mode :)

## Usage

Operators (permission level 2, i.e. gamemaster commands) can toggle the
creative inventory per player:

```
/creative_inv enable [player]
/creative_inv disable [player]
```

Without the optional `player` argument the command applies to whoever
runs it. An enabled player can open the creative inventory as if they
were in creative mode — item picker, pick block, item spawning — while
remaining a regular survival player otherwise.

The flag is saved with the player, survives death and relogging, and is
synced to the affected player only. It is suppressed while the player is
in spectator mode.

The mod must be installed on **both the server and the client**: the
server accepts the creative-slot packets, the client shows the creative
inventory screen.

## Supported versions

| Minecraft       | Loaders          | Where                                                                     |
| --------------- | ---------------- | ------------------------------------------------------------------------- |
| 26.1.2 or newer | Fabric, NeoForge | this fork ([releases](https://github.com/GaborWnuk/Subsidy/releases))     |
| 26.1 snapshots  | Fabric           | [upstream](https://github.com/ThePotatoArchivist/Subsidy)                 |

Required companion mods:

- **Fabric**: Fabric API
- **NeoForge**: none

The mod declares a `26.1.2`+ version range in its metadata, so loaders
refuse to load it on older Minecraft versions instead of crashing.

## Building from source

Requirements: Java 25 (Gradle auto-provisions it if missing thanks to
the Foojay toolchain resolver).

```
./gradlew build
```

builds every version/loader combination; the jars end up in
`versions/<version>-<loader>/build/libs/`. To build or run a single
target:

```
./gradlew :26.1.2-neoforge:build
./gradlew :26.1.2-fabric:runClient
./gradlew :26.1.2-neoforge:runServer
```

The project uses [Stonecutter](https://stonecutter.kikugie.dev/) with one
shared source tree; loader differences live behind `//? if fabric` /
`//? if neoforge` comment switches. To point your IDE at the other
target, run:

```
./gradlew "Set active project to 26.1.2-neoforge"
```

Mod metadata and dependency versions are centralized in
[`stonecutter.properties.toml`](stonecutter.properties.toml).

## Releasing

Pushing a git tag runs the [publish workflow](.github/workflows/publish.yml),
which builds the Fabric and NeoForge jars and attaches both to a GitHub
Release for that tag, with release notes taken from the top entry of
[`CHANGELOG.md`](CHANGELOG.md).

## License

[MIT](LICENSE) — original mod by
[ThePotatoArchivist](https://github.com/ThePotatoArchivist).
