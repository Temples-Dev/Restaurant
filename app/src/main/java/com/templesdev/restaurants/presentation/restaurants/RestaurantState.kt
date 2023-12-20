package com.templesdev.restaurants.presentation.restaurants

import com.templesdev.restaurants.domain.Restaurant

data class RestaurantState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error:String?= null
)