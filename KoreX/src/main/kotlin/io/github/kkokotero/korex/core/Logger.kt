package io.github.kkokotero.korex.core

enum class LogLevel { VERBOSE, DEBUG, INFO, WARN, ERROR }

fun interface Logger {
    fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?)
}

class ConsoleLogger : Logger {
    override fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        val output = buildString {
            append('[').append(level.name).append("] ").append(tag).append(": ").append(message)
            throwable?.let { append(" -> ").append(it.message ?: it::class.simpleName) }
        }
        println(output)
    }
}

class CompositeLogger(private val loggers: List<Logger>) : Logger {
    override fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        loggers.forEach { it.log(level, tag, message, throwable) }
    }
}

fun logger(block: (LogLevel, String, String, Throwable?) -> Unit): Logger = Logger(block)
fun consoleLogger(): Logger = ConsoleLogger()

fun Logger.v(message: String, tag: String = "KoreX") = log(LogLevel.VERBOSE, tag, message, null)
fun Logger.d(message: String, tag: String = "KoreX") = log(LogLevel.DEBUG, tag, message, null)
fun Logger.i(message: String, tag: String = "KoreX") = log(LogLevel.INFO, tag, message, null)
fun Logger.w(message: String, throwable: Throwable? = null, tag: String = "KoreX") = log(LogLevel.WARN, tag, message, throwable)
fun Logger.e(message: String, throwable: Throwable? = null, tag: String = "KoreX") = log(LogLevel.ERROR, tag, message, throwable)
