plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    //dagger
    id("dagger.hilt.android.plugin")
    id("jacoco")

}

jacoco {
    toolVersion = "0.8.8"
}

tasks.register<JacocoReport>("instrumentationCodeCoverage") {
    reports {
        html.required.set(true)
        xml.required.set(true)
    }

    classDirectories.setFrom(
        fileTree("${buildDir}/tmp/kotlin-classes/debug") {
            exclude(
                "**/R.class",
//                "**/R$*.class",
                "**/BuildConfig.*",
//                "**/*Application.*",
//                "**/Manifest*.*",
//                "**/*Test*.*",
//                "**/android/**/*.*",
                "**/di/**",
                "**/Hilt*.*",
                "**/MainActivity.*"
//                "**/androidx/**/*.*",
//                "**/airbnb/**/*.*",
//                "**/di/**/*.*",
//                "**/*Dagger*.*",
//                "**/*Screen*"
            )
        }
    )

    sourceDirectories.setFrom(
        files(
            "src/main/java",
            "src/main/kotlin"
        )
    )

    executionData.setFrom(
        fileTree("${buildDir}/outputs/code_coverage/debugAndroidTest/connected/Medium_Phone_API_35(AVD) - 15") {
            include("*.ec")
        }
    )
}

android {
    namespace = "pl.matiu.kalistenika"
    compileSdk = 36

    defaultConfig {
        applicationId = "pl.matiu.kalistenika"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner";
        testInstrumentationRunner = "pl.matiu.kalistenika.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations += listOf("en", "pl")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = rootProject.extra["sourceCompatibility"] as JavaVersion
        targetCompatibility = rootProject.extra["targetCompatibility"] as JavaVersion
    }

    kotlinOptions {
        jvmTarget = "17"

        allWarningsAsErrors = false

    }
    buildFeatures {
        compose = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.15"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(libs.appcompat)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    implementation(libs.gson)
    implementation(libs.gson.extras)
    implementation(libs.firebase.database)
    implementation(libs.compose.material.icons)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20230300))
    debugImplementation(libs.androidx.ui.tooling)

    //tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //coroutines
    implementation(libs.kotlinx.coroutines.core)

    //room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    //Dagger - hilt tests
    androidTestImplementation(libs.hilt.android.testing)
    testImplementation(libs.dagger.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    kaptAndroidTest(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)//to dodałem
    kaptAndroidTest(libs.hilt.compiler)//i to dodałem i zaczelo dzialac
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.runner)

    //voyager
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel)
    implementation(libs.voyager.bottom.sheet)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)

    //navigation 3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
}

//./gradlew connectedDebugAndroidTest - uruchamia testy i potem tworze raport z pliku ec ./gradlew instrumentationCodeCoverage