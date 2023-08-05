package temples.dev.restaurants.domain

import temples.dev.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {


    suspend operator fun invoke(): List<RestaurantEntity> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }
}