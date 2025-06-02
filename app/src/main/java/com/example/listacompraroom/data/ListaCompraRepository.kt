package com.example.listacompraroom.data

import kotlinx.coroutines.flow.Flow

interface ListaCompraRepository {
    fun getAll(): Flow<List<Products>>
    fun getListaCompra(id: Int): Flow<Products>
    suspend fun insert(products: Products)
    suspend fun update(products: Products)
    suspend fun delete(products: Products)
}

class ListaCompraRepositoryImpl(private val listaCompraDao: ListaCompraDao): ListaCompraRepository {
  override fun getAll(): Flow<List<Products>> = listaCompraDao.getAll()
  override fun getListaCompra(id: Int): Flow<Products> = listaCompraDao.getListaCompra(id)
  override suspend fun insert(products: Products) = listaCompraDao.insert(products)
  override suspend fun update(products: Products) = listaCompraDao.update(products)
    override suspend fun delete(products: Products) = listaCompraDao.delete(products)
}