package com.example.listacompraroom.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listacompraroom.data.Products
import com.example.listacompraroom.ui.state.ListaCompraViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ListaCompraViewModel,
    onProductoClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val productos by viewModel.productos.collectAsState(initial = emptyList())

    var productoAEliminar by remember { mutableStateOf<Products?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ListaProductosTopAppBar(
                title = "ListaCompraApp Andres",
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Producto")
            }
        },
        bottomBar = {
            BottomAppBar {
                val total = productos.sumOf { it.quantity * it.price }
                Text(
                    text = if (productos.isNotEmpty())
                        "Total: %.2f€".format(total)
                    else
                        "Todavía no hay productos añadidos",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    ) { padding ->
        if (productos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(64.dp))
                    Text("No hay productos añadidos todavía")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.insertarProductosPorDefecto() }) {
                        Text("Insertar productos de prueba")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(producto.name, style = MaterialTheme.typography.titleMedium)
                                Text("Cantidad: ${producto.quantity}", style = MaterialTheme.typography.bodyMedium)
                                Text("Precio: ${producto.price}€", style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(onClick = { onProductoClick(producto.id) }) {
                                Icon(Icons.Default.Visibility, contentDescription = "Ver detalle")
                            }
                            IconButton(onClick = {
                                productoAEliminar = producto
                                showDialog = true
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Borrar producto")
                            }
                        }
                    }
                }
            }
        }

        // Diálogo de confirmación de borrado
        ConfirmDeleteDialog(
            show = showDialog,
            poductoName = productoAEliminar?.name ?: "",
            onConfirm = {
                productoAEliminar?.let { viewModel.deleteProducto(it) }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}
