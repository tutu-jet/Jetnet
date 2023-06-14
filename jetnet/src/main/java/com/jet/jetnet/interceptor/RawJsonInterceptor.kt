package com.jet.jetnet.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class RawJsonInterceptor(private val mTag: String = "RawJsonInterceptor") : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body()
        val contentType = responseBody?.contentType()
        val rawJson = responseBody?.string()

        // 将原始 JSON 数据打印出来
        Log.d(mTag, "json原始返回 = $rawJson")

        // 重新构建响应体，因为 Response 的 body 只能读取一次
        val newResponseBody = ResponseBody.create(contentType, rawJson ?: "")

        return response.newBuilder()
            .body(newResponseBody)
            .build()
    }
}