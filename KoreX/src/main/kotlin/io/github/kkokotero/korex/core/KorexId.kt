package io.github.kkokotero.korex.core

@JvmInline
value class KorexId(val value: String) {
    init {
        require(value.isNotBlank()) { "KorexId cannot be blank." }
    }
}

fun String.asKorexId(): KorexId = KorexId(this)
