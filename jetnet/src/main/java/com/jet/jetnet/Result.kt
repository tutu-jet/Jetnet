package com.jet.jetnet

/**
 * @author tujian
 * @date 2023/06/13
 */
sealed class Result<out T> {

    data class Success<T>(val data: T?) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()
}