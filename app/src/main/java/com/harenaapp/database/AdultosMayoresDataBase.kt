package com.harenaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harenaapp.entities.AdultoMayor

@Database(entities = [AdultoMayor::class], version = 1)
abstract class AdultosMayoresDataBase: RoomDatabase() {
    abstract fun getAdultosMayoresDao(): AdultosMayoresDao

    companion object
    {
        private var instancia: AdultosMayoresDataBase? = null

        @Synchronized
        fun getAdultosMayoresDatabase(contexto: Context): AdultosMayoresDataBase?
        {
            if (instancia === null)
                instancia = Room.databaseBuilder(contexto, AdultosMayoresDataBase::class.java, "adultosmayoresdb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            return instancia
        }
    }

}

