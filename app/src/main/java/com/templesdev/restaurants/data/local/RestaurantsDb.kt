package com.templesdev.restaurants.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalRestaurant::class], version = 1, exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantsDao

}
