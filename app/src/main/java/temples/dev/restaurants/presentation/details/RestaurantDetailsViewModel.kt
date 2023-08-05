package temples.dev.restaurants.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import temples.dev.restaurants.data.remote.RestaurantsApiService
import temples.dev.restaurants.domain.RestaurantEntity

class RestaurantDetailsViewModel(stateHandle:SavedStateHandle): ViewModel() {
    private var restInterface: RestaurantsApiService
   val state = mutableStateOf<RestaurantEntity?>(null)

    init {
        val retrofit: Retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://restaurants-b4e11-default-rtdb.firebaseio.com/")
                .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
        val id = stateHandle.get<Int>("restaurant_id") ?: 0

        viewModelScope.launch {
            val restaurant = getRemoteRestaurant(id)
            state.value = restaurant
        }

    }

    private suspend fun getRemoteRestaurant(id: Int): RestaurantEntity {
        return withContext(Dispatchers.IO) {
            val responseMap = restInterface.getRestaurant(id)
            return@withContext responseMap.values.first().let {
                RestaurantEntity(
                    id = it.id,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }

}