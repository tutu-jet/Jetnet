package com.jet.jetnet.base

/**
 * @author tujian
 */
abstract class BaseSingleton<in T, out R> {

    @Volatile
    private var instance: R? = null

    protected abstract fun create(param: T): R

    fun getInstance(param: T) = instance ?: synchronized(this) {
        instance ?: create(param).also { instance = it }
    }

}