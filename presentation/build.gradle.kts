plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.charlie.presentation"
    compileSdk = compilerSdkVersion

    defaultConfig {
        minSdk = minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    viewBinding.enable = true

    dataBinding.enable = true
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependency.core)
    implementation(Dependency.appcompat)
    implementation(Dependency.googleMaterial)
    implementation(Dependency.constraintlayout)
    implementation(Dependency.activity)
    implementation(Dependency.fragment)
    implementation(Dependency.glide)
    implementation(Dependency.saveState)
    implementation(Dependency.hilt)
    kapt(Dependency.hiltCompiler)
    testImplementation(Dependency.junit)
    androidTestImplementation(Dependency.androidjunit)
    androidTestImplementation(Dependency.espresso)
}