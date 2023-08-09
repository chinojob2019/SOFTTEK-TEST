import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.culqitest.softtek_test"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.culqitest.softtek_test"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    viewBinding {
        enable = true
    }

    dataBinding{ enable=true}
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    implementation  ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation  ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation  ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation  ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")
    implementation  ("androidx.room:room-runtime:2.5.2")
    implementation  ("androidx.room:room-ktx:2.5.2")
    implementation  ("androidx.paging:paging-runtime-ktx:3.2.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit-mock:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    ksp("androidx.room:room-compiler:2.5.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


