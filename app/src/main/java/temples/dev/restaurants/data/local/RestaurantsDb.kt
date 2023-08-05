package temples.dev.restaurants.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LocalRestaurant::class], version = 5, exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantsDao


}

