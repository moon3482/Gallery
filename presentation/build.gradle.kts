plugins {
    id(ANDROID_LIBRARY)
    id(KOTLIN)
    kotlin(KAPT)
    id(HILT)
}

android {
    namespace = "com.charlie.presentation"
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

    viewBinding.enable = true

    dataBinding.enable = true
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependency.CORE)
    implementation(Dependency.APPCOMPAT)
    implementation(Dependency.GOOGLE_MATERIAL)
    implementation(Dependency.CONSTRAINTLAYOUT)
    implementation(Dependency.ACTIVITY)
    implementation(Dependency.FRAGMENT)
    implementation(Dependency.GLIDE)
    implementation(Dependency.SAVEDSTATE)
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)
    testImplementation(Dependency.JUNIT)
    androidTestImplementation(Dependency.ANDROID_JUNIT)
    androidTestImplementation(Dependency.ESPRESSO)
}

kapt {
    correctErrorTypes = true
}