package io.github.kkokotero.korex.core

import kotlin.jvm.JvmName

@JvmName("positiveInt")
fun ValidatorBuilder<Int>.positive(message: String = "Value must be positive") {
    rule(message) { it > 0 }
}

@JvmName("nonNegativeInt")
fun ValidatorBuilder<Int>.nonNegative(message: String = "Value must be non-negative") {
    rule(message) { it >= 0 }
}

@JvmName("inRangeInt")
fun ValidatorBuilder<Int>.inRange(min: Int, max: Int, message: String = "Value must be between $min and $max") {
    rule(message) { it in min..max }
}

@JvmName("positiveLong")
fun ValidatorBuilder<Long>.positive(message: String = "Value must be positive") {
    rule(message) { it > 0L }
}

@JvmName("nonNegativeLong")
fun ValidatorBuilder<Long>.nonNegative(message: String = "Value must be non-negative") {
    rule(message) { it >= 0L }
}

@JvmName("inRangeLong")
fun ValidatorBuilder<Long>.inRange(min: Long, max: Long, message: String = "Value must be between $min and $max") {
    rule(message) { it in min..max }
}

@JvmName("positiveDouble")
fun ValidatorBuilder<Double>.positive(message: String = "Value must be positive") {
    rule(message) { it > 0.0 }
}

@JvmName("nonNegativeDouble")
fun ValidatorBuilder<Double>.nonNegative(message: String = "Value must be non-negative") {
    rule(message) { it >= 0.0 }
}

@JvmName("inRangeDouble")
fun ValidatorBuilder<Double>.inRange(min: Double, max: Double, message: String = "Value must be between $min and $max") {
    rule(message) { it in min..max }
}
