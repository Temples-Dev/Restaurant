package temples.dev.restaurants.presentation.list

import temples.dev.restaurants.domain.RestaurantEntity

data class RestaurantsScreenState(
    val restaurants: List<RestaurantEntity>,
    val isLoading : Boolean,
    val error: String? = null
)
