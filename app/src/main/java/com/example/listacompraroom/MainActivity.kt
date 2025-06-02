package com.example.listacompraroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacompraroom.ui.ListaCompraViewModelFactory
import com.example.listacompraroom.ui.navegation.ListaProductosApp
import com.example.listacompraroom.ui.state.ListaCompraViewModel
import com.example.listacompraroom.ui.theme.ListaCompraRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ListaCompraViewModel = viewModel(
                factory = ListaCompraViewModelFactory(application)
            )
            ListaCompraRoomTheme {
                ListaProductosApp(viewModel = viewModel)
            }
        }
    }
}