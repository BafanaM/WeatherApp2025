plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.weatherapp2025"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp2025"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.appcompat:appcompat:1.4.2")
        }
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
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Network Calls
    implementation (libs.retrofit)
    implementation (libs.gson)
    implementation (libs.converter.gson)

    implementation (libs.play.services.location)
    implementation (libs.accompanist.permissions)
    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.constraintlayout.compose)
    implementation (libs.logging.interceptor)
    implementation (libs.lottie)
    implementation (libs.androidx.lifecycle.extensions)

    // Coroutine Lifecycle Scopes
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx.v220)

    //UI
    implementation (libs.ui)
    implementation (libs.androidx.material)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Hilt
    implementation (libs.hilt.android)

    implementation (libs.accompanist.permissions.v0250)
    implementation (libs.coil.compose)

    implementation(libs.androidx.appcompat)

    //Unit Tests
    testImplementation ("io.mockk:mockk:1.13.4")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.8.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.10.0")
    androidTestImplementation ("androidx.test:core:1.5.0")
    testImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")
}