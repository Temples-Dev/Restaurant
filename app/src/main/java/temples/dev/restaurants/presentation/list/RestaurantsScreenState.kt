package temples.dev.restaurants.presentation.list

import temples.dev.restaurants.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading : Boolean,
    val error: String? = null
)
