package com.example.listacompraroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listacompraroom.data.Products
import com.example.listacompraroom.ui.state.ListaCompraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    viewModel: ListaCompraViewModel,
    productId: Int,
    onBack: () -> Unit
) {
    val producto by viewModel.getProductoById(productId).collectAsState(initial = null)
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    /**LaunchedEffect(producto) {
        producto?.let {
            quantity = it.quantity.toString()
            price = it.price.toString()
        }
    }**/

    val datosValidos = quantity.toIntOrNull() != null &&
            price.toDoubleOrNull() != null &&
            quantity.isNotBlank() && price.isNotBlank()

    Scaffold(
        topBar = {
            ListaProductosTopAppBar(
                title = "Actualizar Producto",
                canNavigateBack = true,
                navigateUp = onBack
            )
        }
    ) { padding ->
        producto?.let { prod ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Nombre (no editable)
                OutlinedTextField(
                    value = prod.name,
                    onValueChange = {},
                    label = { Text("Nombre Producto") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                // Cantidad
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Precio
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.replace(",", ".") },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.updateProductos(
                            prod.copy(
                                quantity = quantity.toInt(),
                                price = price.toDouble()
                            )
                        )
                        onBack()
                    },
                    enabled = datosValidos,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("ACTUALIZAR PRODUCTO", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
