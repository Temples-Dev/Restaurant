package com.templesdev.restaurants.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteRestaurantDto(
    @SerializedName("r_id")
    val id: Int,
    @SerializedName("r_title")
    val title: String,
    @SerializedName("r_description")
    val description: String
)