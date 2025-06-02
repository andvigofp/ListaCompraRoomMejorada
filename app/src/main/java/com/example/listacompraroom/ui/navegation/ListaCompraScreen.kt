package com.example.listacompraroom.ui.navegation

sealed class ListaCompraScreen(val route: String) {
  object HomeScreen :  ListaCompraScreen("home")
  object AddProductosScreen : ListaCompraScreen("add_lista")
  object DetalleScreen : ListaCompraScreen("detalle_lista/{productoID}") {
      fun createRoute(listaId: Int) = "detalle_lista/$listaId"
  }
}