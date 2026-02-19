plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.esl_1p"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.esl_1p"
        minSdk = 29
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // 1. Import the Compose BOM (Bill of Materials)
    val composeBom = platform("androidx.compose:compose-bom:2026.01.01") // Use the latest 2026 BOM
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // 2. Add the Material 3 library (no version needed because of BOM)
    implementation("androidx.compose.material3:material3")

    // 3. Optional: Add Material 3 Window Size Class (for tablet/foldable support)
    implementation("androidx.compose.material3:material3-window-size-class")

    // 4. Important: Add Extended Icons (needed for the BackHand icon we used earlier)
    implementation("androidx.compose.material:material-icons-extended")
}