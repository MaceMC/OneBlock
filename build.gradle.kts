plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "org.macemc"
version = "0.1.0"
val author = "tooobiiii"
val targetJavaVersion = 21

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://bitbucket.org/kangarko/libraries/raw/master")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")

    // Foundation and transitive libs
    implementation("com.github.kangarko:Foundation:6.9.17") {
        isTransitive = false
    }
    implementation("org.mineacademy.plugin:WorldGuard:7.0.11")
    implementation("org.mineacademy.plugin:WorldEdit:7.3.6")
    compileOnly("org.mineacademy.plugin:PlaceholderAPI:2.11.6")

    implementation("com.jeff-media:custom-block-data:2.2.3")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersion)
        }
    }

    processResources {
        val props = mapOf("version" to version, "projectName" to project.name, "author" to author)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("**/*.yml") {
            expand(props)
        }
    }

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveClassifier.set("")
        dependencies {
            include(dependency("com.jeff-media:custom-block-data:2.2.3"))
            include(dependency("com.github.kangarko:Foundation:6.9.17"))
        }
        relocate("com.jeff_media.customblockdata", "org.macemc.OneBlock.lib.customblockdata")
    }
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}