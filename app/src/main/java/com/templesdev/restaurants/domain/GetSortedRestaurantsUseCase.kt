package com.templesdev.restaurants.domain

import com.templesdev.restaurants.data.Repository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}