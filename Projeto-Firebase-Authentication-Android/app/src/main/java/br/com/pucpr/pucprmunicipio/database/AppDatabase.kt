package br.com.pucpr.pucprmunicipio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.pucpr.pucprmunicipio.database.dao.MunicipioDAO


import br.com.pucpr.pucprmunicipio.model.Municipio

@Database(
    version = 2,
    entities = [Municipio::class],
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun municipioDao(): MunicipioDAO

}