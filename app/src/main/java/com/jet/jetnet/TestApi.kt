package com.jet.jetnet

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET

interface TestApi {
    @GET("/release/news")
    fun getBaidu(): Response<News>
}

data class News(
    @Field("showapi_res_code") val showApiResCode: Int,
    @Field("showapi_res_error") val showApiResError: String,
    @Field("showapi_res_body") val showApiResBody: ShowApiResBody,
)

data class ShowApiResBody(
    @Field("ret_code") val retCode: Int,
    @Field("pagebean") val pageBean: PageBean,
)

data class PageBean(
    @Field("allPages") val allPages: Int,
    @Field("contentlist") val contentList: ContentList,
)

data class ContentList(
    @Field("allList") val allList: List<String>,
    @Field("pubDate") val pubDate: String,
    @Field("title") val title: String,
    @Field("channelName") val channelName: String,
    @Field("imageurls") val imageUrls: List<String>,
    @Field("source") val source: String,
    @Field("channelId") val channelId: String,
    @Field("nid") val nid: String,
    @Field("link") val link: String,
)