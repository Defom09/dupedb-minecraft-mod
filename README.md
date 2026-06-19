# DupeDB Minecraft Mod

A Fabric mod for Minecraft 1.21.1 that connects to the DupeDB API, allowing you to browse Minecraft exploits directly in-game.

## Features

- 🔍 **Search exploits** - `/dupedb search <query>`
- 📊 **View exploit details** - `/dupedb info <exploit_id>`
- 📈 **Check statistics** - `/dupedb stats`
- 🔐 **OAuth authentication** - Automatic browser-based login
- ⚡ **Non-blocking** - All API calls run off-thread

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.1
2. Download the latest mod JAR from [Releases](https://github.com/Defom09/dupedb-minecraft-mod/releases)
3. Place it in `.minecraft/mods/`
4. Launch Minecraft

## First Time Setup

1. On first API call, a browser window will open automatically
2. Approve access to DupeDB
3. Token is automatically saved - no need to authenticate again!

## Commands

### Search Exploits
```
/dupedb search <query> [page]
/dupedb search elytra
/dupedb search duplication 2
```

### View Exploit Details
```
/dupedb info <exploit_id>
/dupedb info abc12345678
```

### Check Authentication Status
```
/dupedb whoami
```

### View DupeDB Statistics
```
/dupedb stats
```

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.16.0+
- Java 21+

## Building from Source

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/dupedb-mod-1.0.0.jar`

## Configuration

The mod stores your authentication token in:
```
config/dupedbmod/dupedb-token.json
```

Delete this file to re-authenticate with a different account.

## License

MIT License

## Links

- [DupeDB Website](https://dupedb.net)
- [Java API Documentation](https://github.com/DupeDB/java-api)
- [Fabric Documentation](https://fabricmc.net/wiki/)

## Support

For issues with the mod, open an issue on [GitHub](https://github.com/Defom09/dupedb-minecraft-mod/issues)

For DupeDB API issues, visit [DupeDB Support](https://dupedb.net)
