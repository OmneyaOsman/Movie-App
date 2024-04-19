import com.android.build.api.variant.BuildConfigField

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
//    id("kotlin-parcelize")

}

android {
    namespace = "banquemisr.challenge05"
    compileSdk = 34

    defaultConfig {
        applicationId = "banquemisr.challenge05"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
        buildConfig=true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions.unitTests {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
    }
}
androidComponents {
    onVariants {
        it.buildConfigFields.put(
            "BASE_URL", BuildConfigField(
                "String", "\"" + project.properties["BASE_URL"] as String + "\"", "BASE_URL"
            ) )
        it.buildConfigFields.put(
            "ACCESS_TOKEN", BuildConfigField(
                "String", "\"" + project.properties["ACCESS_TOKEN"] as String + "\"", "ACCESS_TOKEN"
            )
        )
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // coroutines
    implementation(libs.kotlinx.coroutines)

    // network
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.interceptor)
    implementation(libs.timber.logs)

    // Coil Compose
    implementation( libs.coil.compose)

    // json parsing
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    //room
//    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
    implementation (libs.androidx.room.paging)

    // Pager Indicator
    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicator)

    implementation(libs.junit)
    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.okhttp.mockserver)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.arch.core)
    androidTestImplementation(libs.room.testing)
//    testImplementation (libs.robolectric)
//    androidTestImplementation(libs.androidx.test.core)


    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.arch.core)


//    androidTestImplementation(TestDependencies.runner)
//    androidTestImplementation(TestDependencies.rules)
//    androidTestImplementation("androidx.test:core:1.5.0")
//    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0"){
//        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
//    }

}