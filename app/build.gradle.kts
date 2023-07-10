plugins {
    id(ANROID_APPLICATION)
    id(KOTLIN)
    kotlin(KAPT)
    id(HILT)
}

android {
    namespace = "com.charlie.gallery"
    compileSdk = COMPIL_SDK_VERSION
    defaultConfig {
        applicationId = APPLICATION_ID
        minSdk = MIN_SDK_VERSION
        targetSdk = TARGET_SDK_VERSION
        versionCode = VERSION_CODE
        versionName = VERSION_NAME

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
    implementation(Dependency.CORE)
    implementation(Dependency.APPCOMPAT)
    implementation(Dependency.GOOGLE_MATERIAL)
    implementation(Dependency.CONSTRAINTLAYOUT)
    implementation(Dependency.GLIDE)
    implementation(Dependency.RETROFIT)
    implementation(Dependency.MOSHI_CONVERTER)
    implementation(Dependency.MOSHI)
    kapt(Dependency.MOSHI_CODEGEN)
    implementation(Dependency.ACTIVITY)
    implementation(Dependency.FRAGMENT)
    implementation(Dependency.SAVEDSTATE)
    implementation(Dependency.OKHTTP)
    implementation(Dependency.OKHTTP_LOGING_INTERCEPTOR)
    implementation(Dependency.TIMBER)
    implementation(Dependency.ROOM_RUNTIME)
    annotationProcessor(Dependency.ROOM_COMPILER)
    implementation(Dependency.ROOM_KTX)
    kapt(Dependency.ROOM_COMPILER)
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)
    testImplementation(Dependency.JUNIT)
    androidTestImplementation(Dependency.ANDROID_JUNIT)
    androidTestImplementation(Dependency.ESPRESSO)
}

kapt {
    correctErrorTypes = true
}