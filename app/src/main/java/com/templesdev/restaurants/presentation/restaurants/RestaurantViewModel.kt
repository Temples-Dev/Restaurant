package com.templesdev.restaurants.presentation.restaurants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.templesdev.restaurants.domain.GetInitialRestaurantsUseCase
import com.templesdev.restaurants.domain.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetInitialRestaurantsUseCase,
    private val toggleRestaurantsUseCase: ToggleRestaurantUseCase
) : ViewModel() {


    private val _state = mutableStateOf(
        RestaurantState(
            restaurants = listOf(),
            isLoading = true
        )
    )

    val state: State<RestaurantState>
        get() = _state

    init {
        getRestaurants()
    }


    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()

            _state.value = _state.value.copy(
                error = exception.message,
                isLoading = false
            )
        }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleRestaurantsUseCase.invoke(id, oldValue)
            _state.value = _state.value.copy(
                restaurants = updatedRestaurants
            )
        }
    }

    private fun getRestaurants() {

        viewModelScope.launch(errorHandler) {
            val restaurants = getRestaurantsUseCase.invoke()

            _state.value = _state.value.copy(
                restaurants = restaurants,
                isLoading = false
            )
        }
    }


}
