// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id(androidApplication) version androidGradleVersion apply false
    id(library) version androidGradleVersion apply false
    id(kotlin) version kotlinVersion apply false
    id(hilt) version hiltVersion apply false
}