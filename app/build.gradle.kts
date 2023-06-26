plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.charlie.gallery"
    compileSdk = compilerSdkVersion
    defaultConfig {
        applicationId = applicationId
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = versionCode
        versionName = versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(Dependency.core)
    implementation(Dependency.appcompat)
    implementation(Dependency.googleMaterial)
    implementation(Dependency.constraintlayout)
    implementation(Dependency.glide)
    implementation(Dependency.retrofit)
    implementation(Dependency.moshiConverter)
    implementation(Dependency.moshi)
    kapt(Dependency.moshiCodegen)
    implementation(Dependency.activity)
    implementation(Dependency.fragment)
    implementation(Dependency.saveState)
    implementation(Dependency.okHttp)
    implementation(Dependency.okHttpLogingInterceptor)
    implementation(Dependency.timber)
    implementation(Dependency.roomRuntime)
    annotationProcessor(Dependency.roomCompiler)
    implementation(Dependency.roomKtx)
    kapt(Dependency.roomCompiler)
    implementation(Dependency.hilt)
    kapt(Dependency.hiltCompiler)
    testImplementation(Dependency.junit)
    androidTestImplementation(Dependency.androidjunit)
    androidTestImplementation(Dependency.espresso)
}

kapt {
    correctErrorTypes = true
}