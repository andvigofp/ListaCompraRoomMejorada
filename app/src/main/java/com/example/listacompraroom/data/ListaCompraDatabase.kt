package com.example.listacompraroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Products::class], version = 1)
abstract class AppDatabse : RoomDatabase() {
    abstract fun listaCompraDao(): ListaCompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabse? =null

        fun getDatabase(appContext: Context): AppDatabse {
            return INSTANCE ?: synchronized(this) {
               val instance = Room.databaseBuilder(
                   appContext,
                   AppDatabse::class.java,
                   "listacompra_database"
               ).build().also {
                   INSTANCE = it
               }
                instance
            }
        }
    }
}

