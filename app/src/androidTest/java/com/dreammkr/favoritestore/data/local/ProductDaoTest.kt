package com.dreammkr.favoritestore.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.productDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetFavoriteProducts() = runTest {
        val product = ProductEntity(
            id = 1,
            title = "Test Product",
            price = 9.99,
            description = "Desc",
            category = "Cat",
            image = "url",
            isFavorite = true
        )
        dao.insertProduct(product)

        val favorites = dao.getFavoriteProducts().first()
        assertEquals(1, favorites.size)
        assertEquals(product.title, favorites[0].title)
    }

    @Test
    fun deleteProductRemovesFromFavorites() = runTest {
        val product = ProductEntity(1, "Title", 10.0, "D", "C", "I", true)
        dao.insertProduct(product)
        dao.deleteProduct(product)

        val favorites = dao.getFavoriteProducts().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun isFavoriteReturnsCorrectStatus() = runTest {
        val product = ProductEntity(1, "Title", 10.0, "D", "C", "I", true)
        dao.insertProduct(product)

        val isFav = dao.isFavorite(1)
        assertTrue(isFav)
    }
}
