package com.himanshu.mvvm.data.network

import com.himanshu.mvvm.data.network.responses.AuthResponse
import com.himanshu.mvvm.data.network.responses.EventResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("add-user-event")
    suspend fun addEvent(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("startDateTime") startDateTime: String,
        @Field("endDateTime") endDateTime: String,
        @Field("location") location: String,
        @Field("uid") uid: Int,
    ): Response<EventResponse>

    @FormUrlEncoded
    @POST("delete-event")
    suspend fun deleteEvent(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("startDateTime") startDateTime: String,
        @Field("endDateTime") endDateTime: String,
        @Field("location") location: String,
    ): Response<EventResponse>

    @GET("get-events/{id}")
    suspend fun getEvents(@Path("id") uid: Int?): Response<EventResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            val okHttpClient = OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://auth-himanshu.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}