package io.github.kkokotero.korex.sample.config

import android.content.Context
import io.github.kkokotero.korex.config.Config
import io.github.kkokotero.korex.config.config

fun sampleConfig(context: Context): Config = config(context, name = "sample_config") {
    string("api_url", "https://example.invalid/api")
    boolean("feature_pipeline", true)
    int("max_retries", 3)
    string("app_name", "KoreX Sample")
}
