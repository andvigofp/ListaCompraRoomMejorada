package com.example.listacompraroom.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.listacompraroom.ui.navegation.ListaCompraScreen.AddProductosScreen
import com.example.listacompraroom.ui.screens.AddDProductosScreen
import com.example.listacompraroom.ui.screens.DetalleScreen
import com.example.listacompraroom.ui.screens.HomeScreen
import com.example.listacompraroom.ui.state.ListaCompraViewModel


@Composable
fun ListaProductosApp(viewModel: ListaCompraViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListaCompraScreen.HomeScreen.route
    ) {
        composable(ListaCompraScreen.HomeScreen.route) {
            HomeScreen(
                viewModel = viewModel,
                onProductoClick =  { listacompraId ->
                    navController.navigate(ListaCompraScreen.DetalleScreen.createRoute(listacompraId))
                },
                onAddClick = {
                    navController.navigate(ListaCompraScreen.AddProductosScreen.route)
                }
            )
        }
        composable(
            route = ListaCompraScreen.DetalleScreen.route,
            arguments = listOf(navArgument("productoID") { type = NavType.IntType })
        ) { backStackEntry ->
            val prodctoId = backStackEntry.arguments?.getInt("productoID") ?: 0
            DetalleScreen(
                viewModel = viewModel,
                productId = prodctoId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(ListaCompraScreen.AddProductosScreen.route) {
            AddDProductosScreen (
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}