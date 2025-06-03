package com.example.listacompraroom.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//DataStore
// Crea una instancia singleton de DataStore llamada "shopping_list_prefs"
private const val PREFERENCES_NAME = "shopping_list_prefs"
val Context.shoppingListPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

// Define las claves de preferencia
private object PreferencesKeys {
    val SORT_ORDER = stringPreferencesKey("sort_order") // Ejemplo: guardar orden de clasificación
}

class ShoppingListPreferencesManager(context: Context) {

    private val dataStore = context.shoppingListPreferencesDataStore

    // Guardar un valor (ejemplo: orden de clasificación)
    suspend fun saveSortOrder(sortOrder: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder
        }
    }

    // Leer un valor como Flow (ejemplo: orden de clasificación)
    val sortOrderFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] ?: "default" // Valor por defecto si no existe
        }
}