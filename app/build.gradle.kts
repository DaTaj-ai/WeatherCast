plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.10"
}

val coroutinesVersion by extra("1.5.2")

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10") // Match KSP version
    }
}

android {
    namespace = "com.example.weathercast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weathercast"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.7" // Updated version
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    //    unitTests.includeAndroidResources = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation(libs.protolite.well.known.types)
    implementation(libs.places)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose-android:2.8.7")

    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Navigation
    val nav_version = "2.8.8"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Serialization for NavArgs
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // LiveData & Compose
    val compose_version = "1.0.0"
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")

    // Material3
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha10")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    implementation("com.exyte:animated-navigation-bar:1.0.0")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:6.5.2")
    implementation("com.google.maps.android:maps-compose-utils:6.5.2")
    implementation("com.google.maps.android:maps-compose-widgets:6.5.2")
    implementation("com.google.maps.android:places-compose:0.1.3")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Accompanist
    implementation("com.google.accompanist:accompanist-drawablepainter:0.35.0-alpha")

    // Lottie
    implementation("com.github.LottieFiles:dotlottie-android:0.4.1")
    implementation("com.airbnb.android:lottie-compose:6.6.4")

    // Testing
    androidTestImplementation("io.mockk:mockk-android:1.13.17")
    androidTestImplementation("io.mockk:mockk-agent:1.13.17")
    testImplementation("io.mockk:mockk-android:1.13.17")
    testImplementation("io.mockk:mockk-agent:1.13.17")

//    // JUnit 5
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
//    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

    // JUnit 4 (for Android tests)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Coroutines Testing
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    // Core Testing Dependencies
    testImplementation("androidx.test:core:1.5.0")
}
