package com.templesdev.restaurants.domain

import com.templesdev.restaurants.data.Repository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: Repository ,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean
    ): List<Restaurant> {
        val newFav = oldValue.not()
         repository.toggleFavoriteRestaurant(id, newFav)
         return getSortedRestaurantsUseCase.invoke()
    }
}