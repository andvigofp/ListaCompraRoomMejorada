package com.example.listacompraroom.data

import android.content.Context

class AppContainer(context: Context) {
    private val appDatabase = AppDatabse.getDatabase(context)
    private val listaCompraDao = appDatabase.listaCompraDao()
    private val listaCompraRepository = ListaCompraRepositoryImpl(listaCompraDao)

    fun provideDiscoRepository(): ListaCompraRepository = listaCompraRepository
}