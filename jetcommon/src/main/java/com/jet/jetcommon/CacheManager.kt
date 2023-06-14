package com.jet.jetcommon

/**
    //How to use
    val manager = CacheManager<User>()
    //put cache
    manager.put("key_1", User(id = 1))
    //get from cache
    val user = manager.get("key_1")
 */
//Build a memory cache manager
class CacheManager<T> {
    private val cache = HashMap<String, T>()
    fun put(key: String, value: T) {
        cache[key] = value
    }

    fun get(key: String): T? {
        return cache[key]
    }
}