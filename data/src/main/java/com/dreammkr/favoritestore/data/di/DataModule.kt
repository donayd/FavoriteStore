package com.dreammkr.favoritestore.data.di

import android.content.Context
import androidx.room.Room
import com.dreammkr.favoritestore.data.local.AppDatabase
import com.dreammkr.favoritestore.data.local.ProductDao
import com.dreammkr.favoritestore.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    internal fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "favorite_store_db"
        ).build()
    }

    @Provides
    internal fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}
