package com.himanshu.mvvm.data.network

import androidx.lifecycle.ReportFragment
import com.himanshu.mvvm.data.network.responses.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {
    //done
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    //done
    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    //done
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

    //done
    @FormUrlEncoded
    @POST("delete-event")
    suspend fun deleteEvent(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("startDateTime") startDateTime: String,
        @Field("endDateTime") endDateTime: String,
        @Field("location") location: String,
    ): Response<EventResponse>

    //done
    @GET("get-events/{id}")
    suspend fun getEvents(@Path("id") uid: Int?): Response<EventResponse>

    //done
    @FormUrlEncoded
    @POST("send-friend-request")
    suspend fun sendFriendRequest(
        @Field("senderUserId") senderUserId: Int,
        @Field("receiverUserId") receiverUserId: Int
    ): Response<FriendRequestResponse>

    //done
    @FormUrlEncoded
    @POST("accept-friend-request")
    suspend fun acceptFriendRequest(
        @Field("requestId") requestId: String
    ): Response<FriendRequestResponse>

    //done
    @FormUrlEncoded
    @POST("reject-friend-request")
    suspend fun rejectFriendRequest(
        @Field("requestId") requestId: String
    ): Response<FriendRequestResponse>

    //done
    @GET("get-pending-requests/{userId}")
    suspend fun getPendingFriendRequests(@Path("userId") userId: Int?): Response<PendingFriendRequestsResponse>

    //done
    @GET("get-user-id/{username}")
    suspend fun getUserIdByUsername(@Path("username") username: String): Response<GetUserIdResponse>

    //done
    @GET("/get-friends/{userId}")
    suspend fun getFriendsList(@Path("userId")userId : Int) : Response<FriendListResponse>

    @POST("delete-friendship/{friendshipId}")
    suspend fun removeFriend(@Path("friendshipId")friendshipId: String) : Response<FriendRequestResponse>

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