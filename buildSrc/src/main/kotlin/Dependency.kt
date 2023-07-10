object Dependency {

    //region AndroidX
    private const val CORE_VERSION = "1.10.1"
    private const val APPCOMPAT_VERSION = "1.6.1"
    private const val CONSTRAINTLAYOUT_VERSION = "2.1.4"
    private const val ACTIVITY_VERSION = "1.7.2"
    private const val FRAGMENT_VERSION = "1.6.0"
    private const val SAVEDSTATE_VERSION = "2.2.0"
    const val CORE = "androidx.core:core-ktx:$CORE_VERSION"
    const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
    const val CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:$CONSTRAINTLAYOUT_VERSION"
    const val ACTIVITY = "androidx.activity:activity-ktx:$ACTIVITY_VERSION"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:$FRAGMENT_VERSION"
    const val SAVEDSTATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$SAVEDSTATE_VERSION"
    //endregion

    //region Google
    private const val GOOGLE_MATERIAL_VERSION = "1.9.0"
    const val GOOGLE_MATERIAL = "com.google.android.material:material:$GOOGLE_MATERIAL_VERSION"
    //endregion

    //region Hilt
    const val HILT = "com.google.dagger:hilt-android:$HILT_VERSION"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$HILT_VERSION"
    //endregion

    //region Room
    private const val ROOM_VERSION = "2.5.1"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    const val ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    //endregion

    //region Retrofit
    private const val RETROFIT_VERSION = "2.9.0"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:$RETROFIT_VERSION"
    //endregion

    //region OkHttp
    private const val OKHTTP_VERSION = "4.11.0"
    private const val OKHTTP_LOGING_INTERCEPTOR_VERSION = "4.10.0"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
    const val OKHTTP_LOGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:$OKHTTP_LOGING_INTERCEPTOR_VERSION"
    //endregion

    //region Moshi
    private const val MOSHI_VERSION = "1.14.0"
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:$MOSHI_VERSION"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:$MOSHI_VERSION"
    //endregion

    //region Glide
    private const val GLIDE_VERSION = "4.15.1"
    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    //endregion

    //region Timber
    private const val TIMBER_VERSION = "5.0.1"
    const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
    //endregion

    //region Testing
    private const val JUNIT_VERSION = "4.13.2"
    private const val ANDROID_JUNIT_VERSION = "1.1.5"
    private const val ESPRESS_CORE_VERSION = "3.5.1"
    const val JUNIT = "junit:junit:$JUNIT_VERSION"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:$ANDROID_JUNIT_VERSION"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:$ESPRESS_CORE_VERSION"
    //endregion


}

