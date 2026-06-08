package io.github.kkokotero.korex.core

import kotlin.jvm.JvmName

@JvmName("collectionNotEmpty")
fun <T> ValidatorBuilder<Collection<T>>.notEmpty(message: String = "Collection must not be empty") {
    rule(message) { it.isNotEmpty() }
}

@JvmName("collectionSizeBetween")
fun <T> ValidatorBuilder<Collection<T>>.sizeBetween(min: Int, max: Int, message: String = "Collection size must be between $min and $max") {
    rule(message) { it.size in min..max }
}

@JvmName("listContainsItem")
fun <T> ValidatorBuilder<List<T>>.containsItem(item: T, message: String = "Collection must contain the required item") {
    rule(message) { item in it }
}

@JvmName("arrayNotEmpty")
fun <T> ValidatorBuilder<Array<T>>.notEmpty(message: String = "Array must not be empty") {
    rule(message) { it.isNotEmpty() }
}
