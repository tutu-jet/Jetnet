package com.jet.jetnet

import retrofit2.http.GET

interface TestApi {

    @GET("")
    fun getBaidu()
}