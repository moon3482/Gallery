plugins {
    id(ANDROID_LIBRARY)
    id(KOTLIN)
    kotlin(KAPT)
    id(HILT)
}

android {
    namespace = "com.charlie.local"
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
    implementation(Dependency.ROOM_RUNTIME)
    annotationProcessor(Dependency.ROOM_COMPILER)
    implementation(Dependency.ROOM_KTX)
    kapt(Dependency.ROOM_COMPILER)
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)
}

kapt {
    correctErrorTypes = true
}