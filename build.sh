#!/bin/bash
# Build script for DupeDB Minecraft Mod

echo "Building DupeDB Minecraft Mod..."
./gradlew clean build

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Build successful!"
    echo "JAR file location: build/libs/dupedb-mod-1.0.0.jar"
    echo ""
    echo "Installation:"
    echo "1. Install Fabric Loader 0.16.0+ for Minecraft 1.21.1"
    echo "2. Place dupedb-mod-1.0.0.jar in .minecraft/mods/"
    echo "3. Launch Minecraft"
else
    echo "✗ Build failed!"
    exit 1
fi
