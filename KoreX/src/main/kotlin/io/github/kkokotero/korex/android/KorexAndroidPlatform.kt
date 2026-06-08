package io.github.kkokotero.korex.android

import android.os.Build

data class AndroidPlatformInfo(
    val sdkInt: Int = Build.VERSION.SDK_INT,
    val release: String = Build.VERSION.RELEASE ?: "unknown",
    val codename: String = Build.VERSION.CODENAME ?: "unknown",
    val manufacturer: String = Build.MANUFACTURER ?: "unknown",
    val model: String = Build.MODEL ?: "unknown",
)

object KorexAndroidPlatform {
    val current: AndroidPlatformInfo
        get() = AndroidPlatformInfo()
}
