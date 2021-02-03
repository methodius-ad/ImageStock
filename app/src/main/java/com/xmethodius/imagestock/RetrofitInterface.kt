package com.xmethodius.imagestock

import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Path;
import retrofit2.http.Query;

interface RetrofitInterface {

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String?,
        @Query("w") width: Int?,
        @Query("h") height: Int?
    ): Call<Image?>?

    @GET("photos")
    fun getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String?
    ): Call<List<Image?>?>?

    @GET("photos/curated")
    fun getCuratedPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String?
    ): Call<List<Image?>?>?

    @GET("photos/random")
    fun getRandomPhoto(
        @Query("collections") collections: String?,
        @Query("featured") featured: Boolean?,
        @Query("username") username: String?,
        @Query("query") query: String?,
        @Query("w") width: Int?,
        @Query("h") height: Int?,
        @Query("orientation") orientation: String?
    ): Call<Image?>?

    @GET("photos/random")
    fun getRandomPhotos(
        @Query("collections") collections: String?,
        @Query("featured") featured: Boolean,
        @Query("username") username: String?,
        @Query("query") query: String?,
        @Query("w") width: Int?,
        @Query("h") height: Int?,
        @Query("orientation") orientation: String?,
        @Query("count") count: Int?
    ): Call<List<Image?>?>?

    @GET("photos/{id}/download")
    fun getPhotoDownloadLink(@Path("id") id: String?): Call<Load?>?

    @GET("search/photos")
    fun searchPhotos(
        @Query("query") query: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("orientation") orientation: String?
    ): Call<TotalResults?>?
}