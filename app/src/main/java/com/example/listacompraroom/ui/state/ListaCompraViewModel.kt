package com.example.listacompraroom.ui.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacompraroom.data.AppDatabase
import com.example.listacompraroom.data.Products
import com.example.listacompraroom.data.startingProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ListaCompraViewModel(application: Application) :  AndroidViewModel(application) {
    private val listaCompraDao = AppDatabase.getDatabase(application).listaCompraDao();


    var showSuccessDialog = MutableStateFlow(false)
    var showErrorDialog = MutableStateFlow(false)
    var showExistsDialog = MutableStateFlow(false)
    var error = MutableStateFlow(false)

    val productos: Flow<List<Products>> = listaCompraDao.getAll()

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
            startingProducts.forEach { products ->
                listaCompraDao.insert(products)
            }
        }
    }

    fun getProductoById(id: Int): Flow<Products> {
        return listaCompraDao.getListaCompra(id)
    }


}