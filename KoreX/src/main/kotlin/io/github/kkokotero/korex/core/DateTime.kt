package io.github.kkokotero.korex.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class KorexDateTime(val epochMillis: Long) {
    fun format(pattern: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return formatter.format(Date(epochMillis))
    }

    fun plusMillis(millis: Long): KorexDateTime = copy(epochMillis = epochMillis + millis)

    companion object {
        fun now(): KorexDateTime = KorexDateTime(System.currentTimeMillis())
    }
}
