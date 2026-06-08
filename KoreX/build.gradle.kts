import java.util.Properties

group = rootProject.group
version = rootProject.version

plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    id("maven-publish")
}
fun Project.readLocalProperty(key: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists()) return null

    val properties = Properties().apply {
        localPropertiesFile.inputStream().use { load(it) }
    }
    return properties.getProperty(key)?.takeIf { it.isNotBlank() }
}

android {
    namespace = "io.github.kkokotero.korex"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    androidResources {
        noCompress += "tflite"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])

                groupId = rootProject.group.toString()
                artifactId = "korex"
                version = rootProject.version.toString()

                pom {
                    name.set("KoreX")
                    description.set("KoreX is an Android-first utility library for better DX, providing reusable building blocks for logic, validation, networking, persistent config, permissions, notifications, intents, and lifecycle helpers.")
                    url.set("https://github.com/kkokotero/KoreX")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("kkokotero")
                            name.set("kkokotero")
                        }
                    }
                    scm {
                        url.set("https://github.com/kkokotero/KoreX")
                        connection.set("scm:git:https://github.com/kkokotero/KoreX.git")
                        developerConnection.set("scm:git:ssh://git@github.com/kkokotero/KoreX.git")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                val repoOwner = project.findProperty("githubOwner")?.toString()
                    ?: project.readLocalProperty("githubOwner")
                    ?: "kkokotero"
                val repoName = project.findProperty("githubRepo")?.toString()
                    ?: project.readLocalProperty("githubRepo")
                    ?: "KoreX"
                url = uri("https://maven.pkg.github.com/$repoOwner/$repoName")
                credentials {
                    username = project.findProperty("gpr.user")?.toString()
                        ?: project.readLocalProperty("gpr.user")
                        ?: System.getenv("GITHUB_ACTOR")
                        ?: ""
                    password = project.findProperty("gpr.key")?.toString()
                        ?: project.readLocalProperty("gpr.key")
                        ?: System.getenv("GITHUB_TOKEN")
                        ?: ""
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
}