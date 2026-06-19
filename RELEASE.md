# DupeDB Minecraft Mod v1.0.0 - Release

## 🎮 Minecraft 1.21.1 Fabric Mod

Browse the DupeDB Minecraft exploit database directly in-game!

---

## 📥 Installation

### Step 1: Install Fabric Loader
1. Download [Fabric Installer](https://fabricmc.net/use/) for Minecraft 1.21.1
2. Run the installer and select **Install Client**
3. Choose Minecraft 1.21.1 as the version
4. Click **Install**

### Step 2: Install DupeDB Mod
1. Download `dupedb-mod-1.0.0.jar` from the release assets below
2. Open your Minecraft mods folder:
   - **Windows:** `%APPDATA%\.minecraft\mods`
   - **macOS:** `~/Library/Application Support/minecraft/mods`
   - **Linux:** `~/.minecraft/mods`
3. Place the JAR file in the mods folder
4. Launch Minecraft with the **Fabric** profile

### Step 3: First Authentication (One-Time)
1. Launch Minecraft
2. Join a world or creative mode
3. Type `/dupedb whoami` in chat
4. A **browser window will open** automatically
5. Click **Approve** to authorize the mod
6. Your token is saved automatically - you won't need to authenticate again!

---

## 🎮 In-Game Commands

### Search for Exploits
```
/dupedb search <query>
/dupedb search elytra
/dupedb search duplication
/dupedb search <query> <page>
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

---

## ✨ Features

- 🔍 **Search Exploits** - Find bugs, dupes, and exploits by keyword
- 📊 **View Details** - See full exploit information including status and versions
- 📈 **Statistics** - Check DupeDB database statistics
- 🔐 **OAuth Login** - Secure browser-based authentication
- ⚡ **Non-Blocking** - All API calls run off-thread, no lag!

---

## 📋 Requirements

- **Minecraft:** 1.21.1
- **Fabric Loader:** 0.16.0 or higher
- **Java:** 21 or higher
- **RAM:** 2GB minimum (4GB recommended)

---

## 🔧 Troubleshooting

### Mod not loading?
- Verify Fabric Loader is installed for 1.21.1
- Check that the JAR is in the correct mods folder
- Make sure you're using the Fabric profile in the launcher

### Authentication not working?
- Delete `config/dupedbmod/dupedb-token.json` to reset
- Try running `/dupedb whoami` again
- Ensure your browser allows popups

### Commands not working?
- Check that you're in-game (not on the menu)
- Verify the mod is loaded (check logs)
- Use exact command syntax: `/dupedb search query`

### Rate limit errors?
- Wait a few minutes before making more requests
- The mod respects DupeDB rate limits

---

## 📚 Documentation

- [DupeDB Website](https://dupedb.net)
- [DupeDB Java API](https://github.com/DupeDB/java-api)
- [Fabric Documentation](https://fabricmc.net/wiki/)
- [GitHub Repository](https://github.com/Defom09/dupedb-minecraft-mod)

---

## 📄 License

MIT License - Free to use and modify

---

## 🐛 Support & Issues

- **Mod Issues:** [Report on GitHub](https://github.com/Defom09/dupedb-minecraft-mod/issues)
- **DupeDB API Issues:** [DupeDB Support](https://dupedb.net)
- **Discord/Community:** See DupeDB website for community links

---

## 🚀 Building from Source

```bash
git clone https://github.com/Defom09/dupedb-minecraft-mod.git
cd dupedb-minecraft-mod
./gradlew build
```

The compiled JAR will be at: `build/libs/dupedb-mod-1.0.0.jar`

---

**Version:** 1.0.0  
**Minecraft:** 1.21.1  
**Fabric Loader:** 0.16.0+  
**Release Date:** June 19, 2026
