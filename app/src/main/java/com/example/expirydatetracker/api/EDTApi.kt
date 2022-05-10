package com.example.expirydatetracker.api

import com.example.expirydatetracker.models.*
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate

interface EDTApi {
    @GET("products")
    fun getProducts():Call<ProductBase>
    @POST("products/add")
    fun addProduct(@Query("id") id: String, @Query("name") name: String, @Query("manufacturer_id") manufacturer_id: Int):Call<Product>
    @DELETE("products/delete/{id}")
    fun deleteProduct(@Path("id") id: String):Call<Product>


    @GET("manufacturers")
    fun getManufactuers():Call<ManufacturerBase>
    @POST("manufacturers/add")
    fun addManufacturer(@Query("name") name: String):Call<Manufacturer>
    @DELETE("/manufacturers/delete/{id}")
    fun deleteManufacturer(@Path("id") id: Int):Call<Manufacturer>

    @POST("users/login")
    fun loginUser(@Query("username") username: String, @Query("password") password: String):Call<LoginResponse>
    @POST("users/register")
    fun registerUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("confirmPassword") confirmPassword: String,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String
    ):Call<String>
    @GET("users/{username}/wishlist")
    fun getWishlist(
        @Path("username") username:String,
        @Query("auth_code") auth_code: String
    ):Call<UserProductWishlistBase>
    @GET("users/{username}/expiryList")
    fun getExpirylist(
        @Path("username") username:String,
        @Query("auth_code") auth_code: String
    ):Call<UserProductExpiryBase>


    @POST("productsUsers/add/e")
    fun addExpiry(
        @Query("username") username:String,
        @Query("productId") id:String,
        @Query("expiryDate") expiryDate:String,
        @Query("auth_code") auth_code: String
    ):Call<UserProductExpiry>


    @POST("productsUsers/add/w")
    fun addWishlistItem(
        @Query("username") username:String,
        @Query("productId") id:String,
        @Query("quantity") quantity:Int,
        @Query("auth_code") auth_code: String
    ):Call<UserProductWishlist>

    @DELETE("productsUsers/delete/e")
    fun deleteExpiry(
        @Query("id") id:Int,
        @Query("auth_code") auth_code: String
    ):Call<String>

    @DELETE("productsUsers/delete/w")
    fun deleteWishlist(
        @Query("username") username: String,
        @Query("productId") product_id:String,
        @Query("auth_code") auth_code: String
    ):Call<String>


}