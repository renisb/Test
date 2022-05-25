package com.example.renata.apitest.api

import com.example.renata.apitest.model.Car
import com.example.renata.apitest.model.Entrance
import com.example.renata.apitest.model.Paid
import com.example.renata.apitest.model.Plate
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.http.*
import retrofit2.http.Headers
import retrofit2.Call as Call

interface JsonPlacerHolderApi {

    @GET
    fun getCars(@Url url:String): Call<List<Car>>

    @Headers("Content-type: application/json")
    @POST()
    fun postEntrance(@Body plate: Plate): Call<Entrance>

    @Headers("Content-type: application/json")
    @POST()
    fun postEntrance1(@Field("plate") plate: String): Call<Entrance>

    @POST("{plate}/pay")
    fun postPaid(@Path("plate") plate:String): Call<Paid>

    @POST("{plate}/out")
    fun postExit(@Path("plate") plate:String): Call<Paid>

    @POST("{plate}")
    fun postTest(@Path("plate") plate:String): Call<Paid>


}
//data class ExamplePostDto(val plate:String)

/*@GET("/example/example2/{id}/logueado")
    fun getExemple(@Path("id") id:String): Call<List<Car>>


    @GET
    suspend fun getCars1(page:Int): Response<Car>{
        return RetrofitInstance.api.getCars1(page)
    }

    @Headers("Content-type: application/json'")
    @POST()
    fun postEntrance(@Url url:String): Call<List<Entrance>>

    @Headers("Content-type: application/json'")
    @POST()
    fun postEntrance(@Body example :ExamplePostDto): Call<Entrance>

    @Headers("Content-type: application/json")
    @POST()
    fun postEntrance(@Body plate: Plate): Call<Entrance>

    @POST("/api/users")
    fun getUserInformation(@Field("name") name: String?, @Field("job") job: String?): Call<User?>?
    */