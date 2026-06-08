package io.github.kkokotero.korex.config

import android.content.Context
import android.content.SharedPreferences

interface ConfigStore {
    fun read(key: String): Any?
    fun write(key: String, value: Any?)
    fun remove(key: String)
    fun snapshot(): Map<String, Any?>
}

class InMemoryConfigStore(
    initial: Map<String, Any?> = emptyMap(),
) : ConfigStore {
    private val values = linkedMapOf<String, Any?>().apply { putAll(initial) }

    override fun read(key: String): Any? = values[key]
    override fun write(key: String, value: Any?) {
        values[key] = value
    }

    override fun remove(key: String) {
        values.remove(key)
    }

    override fun snapshot(): Map<String, Any?> = values.toMap()
}

class SharedPreferencesConfigStore(
    context: Context,
    name: String,
) : ConfigStore {
    private val preferences: SharedPreferences = context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun read(key: String): Any? = preferences.all[key]

    override fun write(key: String, value: Any?) {
        preferences.edit().apply {
            when (value) {
                null -> remove(key)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is Set<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    putStringSet(key, value.filterIsInstance<String>().toSet())
                }
                else -> putString(key, value.toString())
            }
        }.apply()
    }

    override fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    override fun snapshot(): Map<String, Any?> = preferences.all.toMap()
}

class Config internal constructor(
    private val store: ConfigStore,
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T {
        val value = store.read(key) ?: error("Missing config key: $key")
        return value as? T ?: error("Config key '$key' has an unexpected type")
    }

    fun contains(key: String): Boolean = store.read(key) != null

    fun put(key: String, value: Any?) {
        store.write(key, value)
    }

    fun remove(key: String) {
        store.remove(key)
    }

    fun asMap(): Map<String, Any?> = store.snapshot()
}

class ConfigBuilder {
    private val values = linkedMapOf<String, Any?>()

    fun put(key: String, value: Any?) {
        values[key] = value
    }

    fun string(key: String, value: String) = put(key, value)
    fun int(key: String, value: Int) = put(key, value)
    fun boolean(key: String, value: Boolean) = put(key, value)

    internal fun build(store: ConfigStore = InMemoryConfigStore(values.toMap())): Config = Config(store)
}

fun config(block: ConfigBuilder.() -> Unit): Config = ConfigBuilder().apply(block).build()

fun config(
    context: Context,
    name: String = "korex_config",
    block: ConfigBuilder.() -> Unit,
): Config = ConfigBuilder().apply(block).build(
    SharedPreferencesConfigStore(context, name),
)
