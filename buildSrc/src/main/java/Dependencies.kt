object ApplicationId {
    const val id = "me.manulorenzo.moneytransactions"
}

object Modules {
    const val app = ":app"
    const val data_account = ":libraries:data-account"
    const val data_transactions = ":libraries:data-transactions"
    const val repository = ":libraries:repository"
    const val features_account = ":features:account"
    const val features_transactions = ":features:transactions"
    const val presentation = ":libraries:presentation"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val safeArgsNavigation = "2.1.0"
    const val navigationKotlin = "2.1.0"
    const val gradleVersions = "0.27.0"
    const val lifecycleViewModel = "2.1.0"
    const val legacy = "1.0.0"
    const val fragmentTesting = "1.1.0"
    const val extJunit = "1.1.1"
    const val extRules = "1.2.0"
    const val threeTenAbp = "1.4.0"
    const val espresso = "3.2.0"
    const val testRunner = "1.2.0"
    const val coroutinesTest = "1.3.2"
    const val robolectric = "4.3.1"
    const val constraintLayout = "1.1.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.2"
    const val gradle = "3.4.2"
    const val threeTenAbpJakeWharton = "1.2.1"
    const val appcompat = "1.0.2"
    const val design = "1.0.0"
    const val cardview = "1.0.0"
    const val recyclerview = "1.0.0"
    const val ktx = "1.0.0-alpha1"
    const val kotlin = "1.3.50"
    const val moshi = "1.8.0"
    const val lifecycle = "2.0.0"
    const val leakCanary = "2.0-beta-3"
    const val koin = "2.0.0-beta-1"
    const val junit = "4.12"
    const val mockitoKotlin = "2.1.0"
    const val mockitoInline = "3.0.0"
    const val coroutinesCore = "1.3.2"
}

object Plugins {
    const val gradleVersions = "com.github.ben-manes.versions"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val androidLibrary = "com.android.library"
}

object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val gradleKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
    const val leakCanaryAndroid =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val threeTenAbpJakeWharton =
        "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbpJakeWharton}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val gradleVersions =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"
    const val navigationKotlin =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKotlin}"
    const val navigationUiKotlin =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationKotlin}"
    const val safeArgsNavigation =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgsNavigation}"
}

object SupportLibraries {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val lifecycleTesting = "androidx.arch.core:core-testing:${Versions.lifecycle}"
    const val koinTest = "org.koin:koin-test:${Versions.koin}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val threeTenAbp = "org.threeten:threetenbp:${Versions.threeTenAbp}"
    const val extRules = "androidx.test:rules:${Versions.extRules}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
}