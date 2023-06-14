package com.jet.jetnet.base

/**
 * @author tujian
 * @date 2023/06/13
 */
abstract class BaseSingleton<in T, out R> {

    @Volatile
    private var instance: R? = null

    protected abstract fun create(param: T): R

    fun getInstance(param: T) = instance ?: synchronized(this) {
        instance ?: create(param).also { instance = it }
    }

}