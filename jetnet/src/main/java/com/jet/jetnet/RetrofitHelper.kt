package com.jet.jetnet

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

/**
 * @author tujian
 * @date 2023/06/12
 * @desc retrofit helper api
 */
object RetrofitHelper {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)
    }

    suspend fun <T> executeRequest(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(
                    Exception(
                        (response.errorBody() ?: "te-test: unknown error").toString()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}