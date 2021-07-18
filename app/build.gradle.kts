val extras = rootProject.extra

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ru.narod.pentiux.homeworkfordoubletapp"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.jvmTarget = "1.8"
    buildFeatures.viewBinding = true
    packagingOptions.exclude("DebugProbesKt.bin")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${extras["kotlinVersion"]}")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    // Lifecycle libraries
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${extras["lifecycleVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${extras["lifecycleVersion"]}")
    // Navigation libraries
    implementation("androidx.navigation:navigation-fragment-ktx:${extras["navVersion"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${extras["navVersion"]}")
    //Hilt
    implementation("com.google.dagger:hilt-android:${extras["hiltVersion"]}")
    kapt("com.google.dagger:hilt-compiler:${extras["hiltVersion"]}")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    // Room
    implementation("androidx.room:room-runtime:${extras["roomVersion"]}")
    kapt("androidx.room:room-compiler:${extras["roomVersion"]}")
    implementation("androidx.room:room-ktx:${extras["roomVersion"]}")
}