plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.charlie.remote"
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
}

dependencies {
    implementation(Dependency.retrofit)
    implementation(Dependency.moshiConverter)
    implementation(Dependency.moshi)
    kapt(Dependency.moshiCodegen)
    implementation(Dependency.okHttp)
    implementation(Dependency.okHttpLogingInterceptor)
    implementation(Dependency.hilt)
    kapt(Dependency.hiltCompiler)
}