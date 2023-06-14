package com.jet.jetnet

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jet.jetnet.interceptor.RawJsonInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author tujian
 * @date 2023/06/13
 */
object RetrofitHelper {

    private const val mTag: String = "RetrofitHelper"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(RawJsonInterceptor(mTag)) // 添加拦截器
                .build()
        )
    }

    private var mRetrofit: Retrofit? = null

    /**
     * Must be called before createService()
     */
    fun setBaseUrl(baseUrl: String): RetrofitHelper {
        if (mRetrofit == null) {
            updateBaseUrl(baseUrl)
        } else {
            throw IllegalStateException("setBaseUrl: BaseUrl is already set")
        }
        return this
    }

    /**
     * Use with caution, this can mess up the baseUrl.
     */
    private fun updateBaseUrl(baseUrl: String) {
        try {
            mRetrofit = retrofitBuilder.baseUrl(baseUrl).build()
        } catch (e: Exception) {
            Log.d(mTag, "updateBaseUrl: Illegal base url")
            e.printStackTrace()
        }
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return mRetrofit?.create(serviceClass)
            ?: throw IllegalStateException("createService: BaseUrl is not set")
    }

    fun <T> createService(serviceClass: Class<T>, baseUrl: String): T {
        updateBaseUrl(baseUrl)
        return mRetrofit?.create(serviceClass)
            ?: throw IllegalStateException("createService: BaseUrl is not set")
    }

    suspend fun <T> executeRequest(request: () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
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

    fun <T> Result<T>.handleResult(
        successHandler: (T?) -> Unit,
        errorHandler: (Exception) -> Unit,
    ) {
        when (this) {
            is Result.Success -> {
                successHandler(data)
            }

            is Result.Error -> {
                Log.d(mTag, "handleResult: ${exception.message}")
                exception.printStackTrace()
                errorHandler(exception)
            }
        }
    }
}
