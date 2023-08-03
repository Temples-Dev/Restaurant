package temples.dev.restaurants.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//import androidx.room.PrimaryKey


@Entity
data class Restaurant(
    @PrimaryKey
    @ColumnInfo(name = "r_id")
    val id: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
