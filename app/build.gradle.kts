plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version ("1.6.10-1.0.2")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "br.com.alaksion.qrcodereader"
    compileSdk = 31

    defaultConfig {
        applicationId = "br.com.alaksion.qrcodereader"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core-utils"))
    implementation(project(":core-ui"))
    implementation(project(":core-db"))
    implementation(project(":core-platform-utils"))

    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.ktx)

    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)

    implementation(libs.compose.activty)
    androidTestImplementation(libs.compose.androidtest.junit)
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.zxing)

    implementation(libs.bundles.cameraX)

    implementation("io.github.raamcosta.compose-destinations:core:1.2.1-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.2.1-beta")

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.androidX.hilt.navigation)

    implementation(libs.accompanist.insets)
    implementation(libs.androidX.splashscreen)

    implementation(libs.coroutines.core)

}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

kapt {
    correctErrorTypes = true
}