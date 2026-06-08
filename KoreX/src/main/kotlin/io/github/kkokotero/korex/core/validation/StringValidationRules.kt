package io.github.kkokotero.korex.core

private val EmailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
private val UuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-8][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")
private val UrlRegex = Regex("^(https?://)[^\\s/$.?#].[^\\s]*$", RegexOption.IGNORE_CASE)

fun ValidatorBuilder<String>.required(message: String = "Value is required") {
    rule(message) { it.isNotBlank() }
}

fun ValidatorBuilder<String>.blank(message: String = "Value must be blank") {
    rule(message) { it.isBlank() }
}

fun ValidatorBuilder<String>.minLength(min: Int, message: String = "Value must have at least $min characters") {
    rule(message) { it.length >= min }
}

fun ValidatorBuilder<String>.maxLength(max: Int, message: String = "Value must have at most $max characters") {
    rule(message) { it.length <= max }
}

fun ValidatorBuilder<String>.lengthBetween(min: Int, max: Int, message: String = "Value length must be between $min and $max") {
    rule(message) { it.length in min..max }
}

fun ValidatorBuilder<String>.matches(regex: Regex, message: String = "Value has an invalid format") {
    rule(message) { regex.matches(it) }
}

fun ValidatorBuilder<String>.email(message: String = "Value must be a valid email") {
    matches(EmailRegex, message)
}

fun ValidatorBuilder<String>.url(message: String = "Value must be a valid url") {
    matches(UrlRegex, message)
}

fun ValidatorBuilder<String>.uuid(message: String = "Value must be a valid uuid") {
    matches(UuidRegex, message)
}

fun ValidatorBuilder<String>.alphanumeric(message: String = "Value must be alphanumeric") {
    matches(Regex("^[A-Za-z0-9]+$"), message)
}

fun ValidatorBuilder<String>.contains(fragment: String, message: String = "Value must contain '$fragment'") {
    rule(message) { fragment in it }
}

fun ValidatorBuilder<String>.startsWith(prefix: String, message: String = "Value must start with '$prefix'") {
    rule(message) { it.startsWith(prefix) }
}

fun ValidatorBuilder<String>.endsWith(suffix: String, message: String = "Value must end with '$suffix'") {
    rule(message) { it.endsWith(suffix) }
}
