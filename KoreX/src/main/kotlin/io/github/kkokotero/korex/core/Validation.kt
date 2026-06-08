package io.github.kkokotero.korex.core

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Invalid(val reasons: List<String>) : ValidationResult
}

fun interface ValidationRule<T> {
    fun validate(value: T): String?
}

class Validator<T>(
    private val rules: List<ValidationRule<T>>,
) {
    fun validate(value: T): ValidationResult {
        val reasons = rules.mapNotNull { it.validate(value) }
        return if (reasons.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(reasons)
    }

    fun isValid(value: T): Boolean = validate(value) is ValidationResult.Valid

    fun requireValid(value: T) {
        when (val result = validate(value)) {
            ValidationResult.Valid -> Unit
            is ValidationResult.Invalid -> error(result.reasons.joinToString(separator = "; "))
        }
    }
}

class ValidatorBuilder<T> {
    private val rules = mutableListOf<ValidationRule<T>>()

    fun rule(message: String, predicate: (T) -> Boolean) {
        rules += ValidationRule { value -> if (predicate(value)) null else message }
    }

    internal fun build(): Validator<T> = Validator(rules.toList())
}

fun <T> validator(block: ValidatorBuilder<T>.() -> Unit): Validator<T> = ValidatorBuilder<T>().apply(block).build()
