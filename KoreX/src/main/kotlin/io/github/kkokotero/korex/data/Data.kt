package io.github.kkokotero.korex.data

import io.github.kkokotero.korex.core.failure
import io.github.kkokotero.korex.core.Result
import io.github.kkokotero.korex.core.success

fun interface LocalDataSource<Key, Value> {
    fun read(key: Key): Value?
}

interface WritableLocalDataSource<Key, Value> : LocalDataSource<Key, Value> {
    fun write(key: Key, value: Value)
    fun remove(key: Key)
    fun clear()
}

fun interface RemoteDataSource<Key, Value> {
    fun fetch(key: Key): Value
}

fun interface CacheStrategy<Key, Value> {
    fun get(key: Key, fetch: () -> Value): Value
}

data class PaginationState<T>(
    val page: Int = 1,
    val items: List<T> = emptyList(),
    val hasMore: Boolean = true,
)

fun interface Paginator<T> {
    fun load(page: Int): List<T>
}

class InMemoryLocalDataSource<Key, Value> : WritableLocalDataSource<Key, Value> {
    private val storage = linkedMapOf<Key, Value>()

    override fun read(key: Key): Value? = storage[key]
    override fun write(key: Key, value: Value) { storage[key] = value }
    override fun remove(key: Key) { storage.remove(key) }
    override fun clear() { storage.clear() }
}

class SyncCoordinator<Key, Value>(
    private val local: WritableLocalDataSource<Key, Value>,
    private val remote: RemoteDataSource<Key, Value>,
) {
    fun sync(key: Key): Result<Value> = try {
        val value = remote.fetch(key)
        local.write(key, value)
        success(value)
    } catch (error: Throwable) {
        local.read(key)?.let { return success(it) }
        failure(error)
    }
}
