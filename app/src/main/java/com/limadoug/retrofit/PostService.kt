package com.limadoug.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    fun list() : Call<List<PostEntity>>




}