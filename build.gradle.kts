buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.2" apply false
    id("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false

    id("com.google.gms.google-services") version "4.4.2" apply false
}

extra["sourceCompatibility"] = JavaVersion.VERSION_17
extra["targetCompatibility"] = JavaVersion.VERSION_17

//room {
//    shemaDirectory("$projectDir/schemas")
//}