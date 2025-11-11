import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeHotReload)

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()
    
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.essenty.state.keeper)

            this.binaryOption("bundleId", "ru.plovotok.kmp.TestKMPApp")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(projects.shared)

            api(libs.decompose)
            api(libs.decompose.extensions.compose)
            api(libs.decompose.extensions.compose.experimental)
            api(libs.essenty.lifecycle)
            api(libs.essenty.lifecycle.coroutines)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.compottie)
            implementation(libs.kmpalette.core)

            implementation(libs.compose.icons)

            implementation(libs.cupertino.icons.extended)

            implementation("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0-beta01")
        }
        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation("com.github.tkuenneth:nativeparameterstoreaccess:0.1.3")
        }
    }
}

val major = 1
val minor = 0
val patch = 0

val appVersionName = "1.0.0"

android {
    namespace = "ru.plovotok.testkmpapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "ru.plovotok.testkmpapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = major * 10_000 + minor * 100 + patch
        versionName = appVersionName
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "ru.plovotok.testkmpapp.MainKt"

        buildTypes.release.proguard {
            obfuscate = true
            configurationFiles.from(project.file("compose-desktop.pro"))
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Books"
            packageVersion = appVersionName

            this.vendor = "Plovotok"
            this.description = "Test Compose Multiplatform application"
            this.copyright = "Copyright © 2025 Plovotok. All rights reserved."
            this.licenseFile.set(project.file("LICENSE.txt"))

            macOS {
                this.dockName = "Books"
                this.appCategory = "public.app-category.books"
                this.bundleID = "ru.plovotok.testkmpapp"
                iconFile.set(project.file("books-icon.icns"))
                jvmArgs += listOf(
                    "-Dapple.awt.application.appearance=system"
                )

                infoPlist {
                    extraKeysRawXml += macOsDeeplinkPlistProperty
                }
            }
            windows {
                iconFile.set(project.file("books-icon.ico"))
                dirChooser = true
                perUserInstall = true
                menu = true
                shortcut = true
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
        }
    }
}

val macOsDeeplinkPlistProperty = """
    <key>CFBundleURLTypes</key>
	<array>
		<dict>
			<key>CFBundleTypeRole</key>
			<string>None</string>
			<key>CFBundleURLName</key>
			<string>www.plovotok.ru</string>
			<key>CFBundleURLSchemes</key>
			<array>
				<string>compose</string>
			</array>
		</dict>
	</array>
""".trimIndent()