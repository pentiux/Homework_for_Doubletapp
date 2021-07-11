buildscript {
    val kotlinVersion by extra("1.5.20")
    val lifecycleVersion by extra( "2.4.0-alpha02")
    val navVersion by extra("2.4.0-alpha04")
    val hiltVersion by extra("2.37")
    val roomVersion by extra("2.3.0")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }

}


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}