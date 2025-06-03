package com.example.listacompraroom.ui.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacompraroom.data.AppDatabase
import com.example.listacompraroom.data.Products
import com.example.listacompraroom.data.ShoppingListPreferencesManager
import com.example.listacompraroom.data.startingProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ListaCompraViewModel(application: Application) :  AndroidViewModel(application) {
    private val listaCompraDao = AppDatabase.getDatabase(application).listaCompraDao();

    // Suponiendo que ya tienes un Context o lo pasas al ViewModel
    private val preferencesManager = ShoppingListPreferencesManager(application) // Ejemplo: si usas AndroidViewModel

    // Expón el Flow de la preferencia
    val currentSortOrder: Flow<String> = preferencesManager.sortOrderFlow

    var showSuccessDialog = MutableStateFlow(false)
    var showErrorDialog = MutableStateFlow(false)
    var showExistsDialog = MutableStateFlow(false)
    var error = MutableStateFlow(false)

    // Modificamos el Flow de productos para combinarlo con la preferencia de ordenación
    val productos: Flow<List<Products>> = listaCompraDao.getAll().combine(currentSortOrder) { products, sortOrder ->
        when (sortOrder) {
            "name" -> products.sortedBy { it.name }
            "quantity" -> products.sortedBy { it.quantity }
            "price" -> products.sortedBy { it.price }
            else -> products // Orden por defecto (sin ordenar o como venga del DAO)
        }
    }

    fun addListaProducto(products: Products) {
        viewModelScope.launch {
            listaCompraDao.insert(products)
        }
    }

    fun deleteProducto(products: Products) {
        viewModelScope.launch {
            listaCompraDao.delete(products)
        }
    }

    fun updateProductos(products: Products) {
        viewModelScope.launch {
            listaCompraDao.update(products)
        }
    }

    fun setShowSuccessDialog(show: Boolean) {
        showSuccessDialog.value = show
    }

    fun setShowErrorDialog(show: Boolean) {
        showErrorDialog.value = show
    }

    fun setShowExistsDialog(show: Boolean) {
        showExistsDialog.value = show
    }

    fun setError(show: Boolean) {
        error.value = show
    }

    fun insertarProductosPorDefecto() {
        viewModelScope.launch {
            val itemCount = listaCompraDao.countProducts()
            if (itemCount == 0) {
                startingProducts.forEach { product ->
                    listaCompraDao.insert(product)
                }
            }
        }
    }

    fun getProductoById(id: Int): Flow<Products> {
        return listaCompraDao.getListaCompra(id)
    }

    // Ejemplo de cómo guardar una preferencia (llamado desde una función del ViewModel)
    fun setSortOrder(newOrder: String) {
        viewModelScope.launch {
            preferencesManager.saveSortOrder(newOrder)
        }
    }
}