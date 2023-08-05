package temples.dev.restaurants.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// domain model component - remove all third party dependency


data class RestaurantEntity(  // Restaurant Entity
    val id: Int,
    val title: String,
    val description: String,
    val isFavorite: Boolean = false
)
