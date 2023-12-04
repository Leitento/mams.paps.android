import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.mams.paps"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mams.paps"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String", "MAPKIT_API_KEY",
            "\"${properties.getProperty("MAPKIT_API_KEY", "")}\""
        )
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.navigation)
    implementation(libs.viewbindingdelegate)
    implementation(libs.yandex.mapkit)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
