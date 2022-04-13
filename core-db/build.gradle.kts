plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "br.com.alaksion.core_db"
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(project(":core-utils"))
    implementation(project(":core-platform-utils"))

    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.coroutines.test)

    implementation(libs.androidX.core.ktx)

    api(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

}

kapt {
    correctErrorTypes = true
}