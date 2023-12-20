package com.templesdev.restaurants.data.remote


import retrofit2.http.GET

interface RestaurantApiService {

    @GET("restaurants.json")
    suspend fun getRestaurants(): List<RemoteRestaurantDto>

//    @GET("restaurants.json?orderBy=\"r_id\"")
//    suspend fun getRestaurant(@Query("equalTo") id: Int): Map<String, RemoteRestaurantDto>
}