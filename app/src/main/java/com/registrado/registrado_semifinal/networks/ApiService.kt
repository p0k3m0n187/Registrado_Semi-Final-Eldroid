package com.registrado.registrado_semifinal.networks

import retrofit2.Call
import com.registrado.registrado_semifinal.models.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface ApiService {

    @GET("/tweet/registrado")
    fun getPostList(): Call<List<Post>>

    @GET("/tweet/registrado/{id}")
    fun getPostById(@Path("id") id: String): Call<Post>

    @POST("/tweet/registrado")
    fun createPost(@Body post: Post): Call<Post>

    @PUT("/tweet/registrado/{id}")
    fun updatePost(@Path("id") id: String, @Body post: Post): Call<Post>

    @DELETE("/tweet/registrado/{id}")
    fun deletePost(@Path("id") id: String): Call<Void>
}
