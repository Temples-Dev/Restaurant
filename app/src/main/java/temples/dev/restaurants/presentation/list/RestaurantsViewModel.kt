package temples.dev.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import temples.dev.restaurants.domain.GetInitialRestaurantsUseCase
import temples.dev.restaurants.domain.ToggleRestaurantUseCase


class RestaurantsViewModel : ViewModel() {

//    private val repository = RestaurantsRepository()
    private val getRestaurantsUseCase = GetInitialRestaurantsUseCase()
    private val toggleRestaurantUseCase = ToggleRestaurantUseCase()

    private val _state = mutableStateOf(
        RestaurantsScreenState(restaurants = listOf(), isLoading = true)
    )

    val state: State<RestaurantsScreenState>
        get() = _state


    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message, isLoading = false
        )
    }


    init {
        getRestaurants()
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {

        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleRestaurantUseCase(id, oldValue)
            _state.value = _state.value.copy(
                restaurants = updatedRestaurants
            )
        }
    }


    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {

            val restaurants = getRestaurantsUseCase()

            _state.value = _state.value.copy(
                restaurants = restaurants, isLoading = false
            )


        }

    }


}
