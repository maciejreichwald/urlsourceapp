package com.rudearts.urlsource.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RestAPI {

    @GET
    fun loadWebsiteSource(@Url url: String): Single<Response<ResponseBody>>

}
