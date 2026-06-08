package io.github.kkokotero.korex.intents

import android.content.Context
import android.content.Intent
import android.net.Uri

data class KorexIntent(val intent: Intent)

fun interface IntentLauncher {
    fun launch(intent: KorexIntent)
}

object IntentActions {
    fun openSettings(context: Context): KorexIntent = KorexIntent(
        Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )

    fun openUrl(url: String): KorexIntent = KorexIntent(
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )

    fun shareContent(subject: String, text: String): KorexIntent = KorexIntent(
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )

    fun sendEmail(to: String, subject: String, body: String): KorexIntent = KorexIntent(
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )

    fun callPhone(number: String): KorexIntent = KorexIntent(
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        },
    )
}

class AndroidIntentLauncher(private val context: Context) : IntentLauncher {
    override fun launch(intent: KorexIntent) {
        context.startActivity(intent.intent)
    }
}
