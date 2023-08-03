package temples.dev.restaurants.domain

import temples.dev.restaurants.data.RestaurantsRepository

class GetSortedRestaurantsUseCase {

    private val repository: RestaurantsRepository =
        RestaurantsRepository()

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}