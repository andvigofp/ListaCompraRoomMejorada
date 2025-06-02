package com.example.listacompraroom.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.listacompraroom.ListaCompraAplication
import com.example.listacompraroom.ui.state.ListaCompraViewModel

class ListaCompraViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaCompraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaCompraViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

fun CreationExtras.listasComprasApp(): ListaCompraAplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ListaCompraAplication)