package com.islaharper.dawnofandroid.data.remote

import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApi {
    // Send token to BE for verification
    @POST("/token_verification")
    suspend fun verifyTokenOnBackend(@Body request: ApiTokenRequest): ApiResponse

    // Remove user from BE Database
    @DELETE("/delete_user")
    suspend fun deleteUser(): ApiResponse

    // Get all android flavours
    @GET("/get_all_flavours")
    suspend fun getAllFlavours(@Query("page") page: Int = 1): ApiResponse

    // search for an android flavour
    @GET("/search_flavour")
    suspend fun searchForFlavour(@Query("name") query: String): ApiResponse
}
