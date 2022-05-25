package com.example.renata.apitest.api

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class HttpHelper {
    fun get() {
        val URL = "https://parking-lot-to-pfz.herokuapp.com/parking/"
        val client = OkHttpClient()
        var request = Request.Builder().url(URL).get().build()
        var response = client.newCall(request).execute()
        val responseBody = response.body
        if (responseBody != null) {
            val json = responseBody.toString()
            println("RESPOSTA: " + json)
        }
        //return response.body().toString()
    }

    fun post(plate:String):String{
        val url = "https://parking-lot-to-pfz.herokuapp.com/parking"
        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaType()
        val body = RequestBody.create(JSON, "{\"plate\":\"$plate\"}")
        val request = Request.Builder()
            //.addHeader("Authorization", "Bearer $token")
            .url(url)
            .post(body)
            .build()

        val  response = client.newCall(request).execute()

        println(response.request)
        println(response.body!!.string())
        return response.body!!.string()
    }
   /* fun post (json:String,plate :String) : String {
        val URL = "https://parking-lot-to-pfz.herokuapp.com/parking/"
        //val headerHttp = MediaType.parse("application/json")//"application/json".toMediaTypeOrNull()
        val client = OkHttpClient()
        val body = RequestBody.create()//.create(headerHttp,json)
        var request = Request.Builder().url(URL).header(plate).post(body).build()
        var response = client.newCall(request).execute()
        return response.body.toString()//.body.toString()
    }*/

}