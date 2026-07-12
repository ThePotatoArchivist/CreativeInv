# v1.1.1

- (NeoForge) Fixed the creative inventory flag no longer working after reconnecting to the server: NeoForge silently drops zero-byte attachment payloads from the initial login sync, so the sync codec now writes a marker byte

# v1.1.0

- Added NeoForge support for Minecraft 26.1.2
- Updated to Minecraft 26.1.2 (release; was 26.1 snapshot 7)
- Restructured into a multiloader project: one codebase builds both the Fabric and NeoForge jars

# v1.0.1

- (Fabric, upstream) Port to the Minecraft 26.1 snapshots

# v1.0.0

- (Fabric, upstream) Initial release
