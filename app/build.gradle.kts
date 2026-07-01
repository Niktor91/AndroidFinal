import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.google.gms.google.services)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use {
            load(it)
        }
    }
    }

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String","API_KEY","\"Bearer ${localProperties["API_KEY"] ?: ""}\"")
            buildConfigField("String","BASE_URL","\"https://api.themoviedb.org/3/\"")
            buildConfigField("String","IMAGE_BASE_URL","\"https://image.tmdb.org/t/p/w500\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","API_KEY","\"Bearer ${localProperties["API_KEY"] ?: ""}\"")
            buildConfigField("String","BASE_URL","\"https://api.themoviedb.org/3/\"")
            buildConfigField("String","IMAGE_BASE_URL","\"https://image.tmdb.org/t/p/w500\"")


        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin{
        jvmToolchain(11)
    }

    buildFeatures{
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.glide)
    implementation(libs.okHttp)
    implementation(libs.retrofit.serialization)
    implementation(libs.serialization.json)
    implementation(libs.android.nav.ui.ktx)
    implementation(libs.android.nav.fragment)
}