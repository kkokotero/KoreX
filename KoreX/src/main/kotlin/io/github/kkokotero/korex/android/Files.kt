package io.github.kkokotero.korex.android

import java.io.File

data class AndroidFile(val file: File) {
    fun exists(): Boolean = file.exists()
    fun text(): String = file.readText()
    fun writeText(content: String) = file.writeText(content)
    fun appendText(content: String) = file.appendText(content)
}
