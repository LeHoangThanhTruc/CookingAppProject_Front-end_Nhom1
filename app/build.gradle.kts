import org.gradle.kotlin.dsl.dependencies

val apiBaseUrlProperty = providers.gradleProperty("API_BASE_URL")
val apiBaseUrl = apiBaseUrlProperty
    .orElse("http://10.0.2.2:8000/")
    .get()
    .let { if (it.endsWith("/")) it else "$it/" }
val releaseTaskRequested = gradle.startParameter.taskNames.any {
    it.contains("release", ignoreCase = true)
}

if (releaseTaskRequested && (!apiBaseUrlProperty.isPresent || !apiBaseUrl.startsWith("https://"))) {
    throw GradleException("Release builds require -PAPI_BASE_URL=https://your-api.example/")
}

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cookingapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cookingapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.okhttp.mockwebserver)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.glide)
    testImplementation(libs.okhttp.mockwebserver)
}
