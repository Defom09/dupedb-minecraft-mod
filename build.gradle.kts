plugins {
    id("fabric-loom") version "1.6.13"
    id("maven-publish")
}

version = "1.0.0"
group = "net.defom"
base.archivesName.set("dupedb-mod")

repositories {
    mavenCentral()
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())
    
    modImplementation("net.fabricmc:fabric-loader:0.16.0")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")
    
    // DupeDB Java API
    implementation("com.github.DupeDB:java-api:1.0.3")
}

tasks.withType<ProcessResources> {
    inputs.property("version", project.version)
    filteringCharacteristics = mapOf("version" to project.version)
    filesNotMatching(listOf("**/*.png")) {
        expand(mapOf("version" to project.version))
    }
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}
