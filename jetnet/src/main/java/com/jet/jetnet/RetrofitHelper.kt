package com.jet.jetnet

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
    }

    private var mRetrofit : Retrofit? = null

    /**
     * Must be called before createService()
     */
    fun setBaseUrl(baseUrl: String) {
        if (mRetrofit == null) {
            mRetrofit = retrofitBuilder.baseUrl(baseUrl).build()
        } else {
            throw IllegalStateException("RetrofitHelper: BaseUrl is already set")
        }
    }

    /**
     * Use with caution, this can mess up the baseUrl.
     */
    fun updateBaseUrl(baseUrl: String) {
        mRetrofit = retrofitBuilder.baseUrl(baseUrl).build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return mRetrofit?.create(serviceClass)
            ?: throw IllegalStateException("RetrofitHelper: BaseUrl is not set")
    }

    suspend fun <T> executeRequest(request: suspend () -> retrofit2.Response<T>): Result<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
