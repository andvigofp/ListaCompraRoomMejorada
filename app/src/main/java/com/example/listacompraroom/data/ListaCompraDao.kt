package com.example.listacompraroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ListaCompraDao {
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAll(): Flow<List<Products>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getListaCompra(id: Int): Flow<Products>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Products)

    @Update
    suspend fun update(product: Products)

    @Delete
    suspend fun delete(product: Products)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun countProducts(): Int
}