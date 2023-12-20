package com.templesdev.restaurants.data.di

import android.content.Context
import androidx.room.Room
import com.templesdev.restaurants.data.Repository
import com.templesdev.restaurants.data.local.RestaurantsDao
import com.templesdev.restaurants.data.local.RestaurantsDb
import com.templesdev.restaurants.data.remote.RestaurantApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // module
@InstallIn(SingletonComponent::class)  // component // inject
object Module {

    @Provides
    fun provideRoomDao(database: RestaurantsDb): RestaurantsDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context
    ): RestaurantsDb {
        return Room.databaseBuilder(
            appContext, RestaurantsDb::class.java, "restaurants_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RestaurantApiService {
        return retrofit.create(RestaurantApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Repository.BASE_URL)
            .build()
    }

}