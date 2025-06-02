package com.example.listacompraroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listacompraroom.ui.state.ListaCompraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    viewModel: ListaCompraViewModel,
    productId: Int,
    onBack: () -> Unit
) {

    val productos by viewModel.productos.collectAsState(initial = emptyList())
    val producto = productos.find { it.id == productId }

    Scaffold(
        topBar = {
            ListaProductosTopAppBar(
                title = "Detalle",
                canNavigateBack = true,
                navigateUp = onBack
            )
        }
    ) { padding ->
        producto?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nombre: ${it.name}")
                Text("Cantidad: ${it.quantity}")
                Text("Precio: ${it.price}")
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado")
        }
    }
}
