package com.templesdev.restaurants.data

import com.templesdev.restaurants.data.local.LocalRestaurant
import com.templesdev.restaurants.data.local.PartialLocalRestaurant
import com.templesdev.restaurants.data.local.RestaurantsDao
import com.templesdev.restaurants.data.remote.RestaurantApiService
import com.templesdev.restaurants.domain.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class Repository @Inject constructor(
    private val restInterface: RestaurantApiService,
    private val restaurantsDao: RestaurantsDao
) {

    suspend fun  fetchRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. We have no data.")
                    }
                    else -> throw e
                }
            }

        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                isFavorite = false
            )
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(it.id, isFavorite = true)
            })
    }

    suspend fun toggleFavoriteRestaurant(
        id: Int,
        value: Boolean
    ) = withContext(Dispatchers.IO) {
        restaurantsDao.update(
            PartialLocalRestaurant(
                id = id,
                isFavorite = !value
            )
        )

    }


    /* [getRestaurant]fun gets the details
    * data from the local database */

    suspend fun getRestaurant(id: Int): LocalRestaurant =
        withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.get(id)
        }

    suspend fun getRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {

                Restaurant(it.id, it.title,
                    it.description, it.isFavorite)

            }
        }
    }



    companion object {
        const val BASE_URL = "https://restaurants-b4e11-default-rtdb.firebaseio.com"
    }
}