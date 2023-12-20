package com.templesdev.restaurants.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.templesdev.restaurants.data.Repository
import com.templesdev.restaurants.data.local.LocalRestaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val repository :Repository
    ) : ViewModel() {


    private val _state = mutableStateOf<LocalRestaurant?>(null)

    val state: State<LocalRestaurant?>
        get() = _state

    init {
        val id = stateHandle.get<Int>("restaurant_id") ?: 0

        viewModelScope.launch {
            val restaurant = repository.getRestaurant(id)
            _state.value = restaurant
        }
    }


}