
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") // This is essential for annotation processing
}

android {
    namespace = "com.example.ruleengine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ruleengine"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(files("C:\\Android_Project\\RuleEngine\\libs\\jtds-1.3.1.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /* Responsive side */
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Other dependencies
    implementation("com.microsoft.sqlserver:mssql-jdbc:9.4.0.jre11")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    // Kap dependency
    implementation("androidx.room:room-runtime:2.6.1")
    kapt(libs.androidx.room.compiler.v250) // Make sure kapt is used here
    implementation("androidx.room:room-ktx:2.6.1")// Optional, for Kotlin Extensions
}