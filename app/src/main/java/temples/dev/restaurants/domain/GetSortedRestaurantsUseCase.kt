package temples.dev.restaurants.domain

import temples.dev.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {


    suspend operator fun invoke(): List<RestaurantEntity> {
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}