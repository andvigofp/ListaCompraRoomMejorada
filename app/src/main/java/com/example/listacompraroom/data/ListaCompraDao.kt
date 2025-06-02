package com.example.listacompraroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ListaCompraDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Products>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getListaCompra(id: Int): Flow<Products>

    @Insert
    suspend fun insert(products: Products)

    @Update
    suspend fun update(products: Products)

    @Delete
    suspend fun delete(products: Products)

}