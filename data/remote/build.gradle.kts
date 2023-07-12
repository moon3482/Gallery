plugins {
    id(ANDROID_LIBRARY)
    id(KOTLIN)
    kotlin(KAPT)
    id(HILT)
}

android {
    namespace = "com.charlie.remote"
    compileSdk = COMPIL_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
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
    implementation(Dependency.RETROFIT)
    implementation(Dependency.MOSHI_CONVERTER)
    implementation(Dependency.MOSHI)
    kapt(Dependency.MOSHI_CODEGEN)
    implementation(Dependency.OKHTTP)
    implementation(Dependency.OKHTTP_LOGING_INTERCEPTOR)
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)
}

kapt {
    correctErrorTypes = true
}