package com.templesdev.restaurants.domain

import com.templesdev.restaurants.data.Repository
import javax.inject.Inject

class  GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: Repository ,
    private val getSortedRestaurantsUseCase : GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(): List<Restaurant> {
        repository.fetchRestaurants()
        return getSortedRestaurantsUseCase()
    }
}
