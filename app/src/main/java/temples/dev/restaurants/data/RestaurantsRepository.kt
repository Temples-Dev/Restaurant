package temples.dev.restaurants.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import temples.dev.RestaurantsApplication
import temples.dev.restaurants.data.local.LocalRestaurant
import temples.dev.restaurants.data.local.PartialLocalRestaurant
import temples.dev.restaurants.data.local.RestaurantsDao
import temples.dev.restaurants.data.local.RestaurantsDb
import temples.dev.restaurants.data.remote.RestaurantsApiService
import temples.dev.restaurants.domain.RestaurantEntity
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao
) {


    suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) =
        withContext(Dispatchers.IO) {
            restaurantsDao.update(
                PartialLocalRestaurant(id = id, isFavorite = value)
            )

        }

    suspend fun loadRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception("something went wrong. " + "We have no data.")
                    }

                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface
            .getRestaurants()

        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                false
            )
        })


        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(it.id, true)
            }
        )
    }

    suspend fun getRestaurants(): List<RestaurantEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {
                RestaurantEntity(it.id, it.title, it.description, it.isFavorite)
            }
        }
    }
}