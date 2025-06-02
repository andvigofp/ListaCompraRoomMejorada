package com.example.listacompraroom

import android.app.Application
import com.example.listacompraroom.data.AppContainer

class ListaCompraAplication: Application(){
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}