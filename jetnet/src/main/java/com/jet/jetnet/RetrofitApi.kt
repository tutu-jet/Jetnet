package com.jet.jetnet

import androidx.annotation.Keep
import com.jet.jetnet.base.BaseSingleton
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author tujian
 */
class RetrofitApi private constructor(baseUrl: String) {

    companion object : BaseSingleton<String, RetrofitApi>() {

        @Keep
        private val mTag = RetrofitApi::class.simpleName
        override fun create(param: String): RetrofitApi {
            return RetrofitApi(param)
        }
    }

    val retrofit: Retrofit
    by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
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