object Dependency {

    //region AndroidX
    private const val coreVersion = "1.10.1"
    private const val appcompatVersion = "1.6.1"
    private const val constraintlayoutVersion = "2.1.4"
    private const val activityVersion = "1.7.2"
    private const val fragmentVersion = "1.6.0"
    private const val saveStateVersion = "2.2.0"
    const val core = "androidx.core:core-ktx:$coreVersion"
    const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"
    const val activity = "androidx.activity:activity-ktx:$activityVersion"
    const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
    const val saveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$saveStateVersion"
    //endregion

    //region Google
    private const val googleMaterialVersion = "1.9.0"
    const val googleMaterial = "com.google.android.material:material:$googleMaterialVersion"
    //endregion

    //region Hilt
    const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    //endregion

    //region Room
    private const val roomVersion = "2.5.1"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    //endregion

    //region Retrofit
    private const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    //endregion

    //region OkHttp
    private const val okHttpVersion = "4.11.0"
    private const val okHttpLogingInterceptorVersion = "4.10.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpLogingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:$okHttpLogingInterceptorVersion"
    //endregion

    //region Moshi
    private const val moshiVersion = "1.14.0"
    const val moshi = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    //endregion

    //region Glide
    private const val glideVersion = "4.15.1"
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    //endregion

    //region Timber
    private const val timberVersion = "5.0.1"
    const val timber = "com.jakewharton.timber:timber:$timberVersion"
    //endregion

    //region Testing
    private const val junitVersion = "4.13.2"
    private const val androidjunitVersion = "1.1.5"
    private const val espressoCoreVersion = "3.5.1"
    const val junit = "junit:junit:$junitVersion"
    const val androidjunit = "androidx.test.ext:junit:$androidjunitVersion"
    const val espresso = "androidx.test.espresso:espresso-core:$espressoCoreVersion"
    //endregion


}

