package com.templesdev.restaurants.domain

// DTO

data class Restaurant(
    val id: Int,
    val title: String,
    val description: String,
    var isFavorite: Boolean = false
)
