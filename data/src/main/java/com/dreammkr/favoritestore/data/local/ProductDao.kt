package com.dreammkr.favoritestore.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {
    @Query("SELECT * FROM products")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("SELECT id FROM products")
    fun getFavoriteIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT EXISTS(SELECT * FROM products WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
