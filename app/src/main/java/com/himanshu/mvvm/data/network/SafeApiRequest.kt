package com.himanshu.mvvm.data.network


import android.util.Log
import com.himanshu.mvvm.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T:Any> apiRequest(call : suspend () -> Response<T>):T{
        try {
            val response = call.invoke()
            Log.d("Nana",response.isSuccessful.toString())
            Log.d("Nana", response.errorBody().toString())
            if(response.isSuccessful){
                return response.body()!!
            }
            else{
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("error"))
                    }catch (e:JSONException){
                        throw e
                    }
                    message.append("\n")
                }
                message.append("Error Code: ${response.code()}")
                throw ApiException(message = message.toString())
            }
        }catch (e:Exception){
            throw ApiException(e.message.toString())
        }
    }
}