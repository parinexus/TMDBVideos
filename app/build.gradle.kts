plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "parinexus.tmdb.movies"
    compileSdk = 35

    defaultConfig {
        applicationId = "parinexus.tmdb.movies"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation("androidx.fragment:fragment:1.6.2")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
    implementation("androidx.savedstate:savedstate:1.2.1")

    // Compose
    implementation(libs.activity.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.icons.extended)
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.compose.runtime.livedata)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Compose Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Dagger Hilt

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Coil Video Loading
    implementation(libs.coil.compose)

    //Paging
    implementation(libs.paging.compose)

    //Exoplayer
    implementation(libs.media3.ui)
    implementation(libs.media3.core)

    // Test
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    testImplementation(libs.core.testing)
    testImplementation(libs.robolectric)
    debugImplementation(libs.test.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.mockwebserver)
}