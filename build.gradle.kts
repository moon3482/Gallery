// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id(ANROID_APPLICATION) version ANDROID_GRADLE_VERSION apply false
    id(ANDROID_LIBRARY) version ANDROID_GRADLE_VERSION apply false
    id(KOTLIN) version KOTLIN_VERSION apply false
    id(HILT) version HILT_VERSION apply false
}