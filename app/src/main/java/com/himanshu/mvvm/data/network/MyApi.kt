package com.himanshu.mvvm.data.network

import com.himanshu.mvvm.data.network.responses.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    // Auth Routes
    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun userSignUp(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("auth/get-user-id/{username}")
    suspend fun getUserIdByUsername(@Path("username") username: String): Response<GetUserIdResponse>

    // Event Routes
    @FormUrlEncoded
    @POST("events/add-user-event")
    suspend fun addEvent(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("startDateTime") startDateTime: String,
        @Field("endDateTime") endDateTime: String,
        @Field("location") location: String,
        @Field("uid") uid: Int
    ): Response<EventResponse>

    @FormUrlEncoded
    @POST("events/delete-event")
    suspend fun deleteEvent(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("startDateTime") startDateTime: String,
        @Field("endDateTime") endDateTime: String,
        @Field("location") location: String
    ): Response<EventResponse>

    @GET("events/get-events/{id}")
    suspend fun getUserEvents(@Path("id") uid: Int): Response<EventResponse>

    // Friend Routes
    @FormUrlEncoded
    @POST("friends/send-friend-request")
    suspend fun sendFriendRequest(
        @Field("senderUserId") senderUserId: Int,
        @Field("receiverUserId") receiverUserId: Int
    ): Response<FriendRequestResponse>

    @FormUrlEncoded
    @POST("friends/accept-friend-request")
    suspend fun acceptFriendRequest(@Field("requestId") requestId: String): Response<FriendRequestResponse>

    @FormUrlEncoded
    @POST("friends/reject-friend-request")
    suspend fun rejectFriendRequest(@Field("requestId") requestId: String): Response<FriendRequestResponse>

    @GET("friends/get-pending-requests/{userId}")
    suspend fun getPendingFriendRequests(@Path("userId") userId: Int?): Response<PendingFriendRequestsResponse>

    @GET("friends/get-friends/{userId}")
    suspend fun getFriendsList(@Path("userId") userId: Int?): Response<FriendListResponse>

    @POST("friends/delete-friendship/{friendshipId}")
    suspend fun removeFriend(@Path("friendshipId") friendshipId: String): Response<FriendRequestResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            val okHttpClient = OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://think-sync-backend.vercel.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}
