package br.com.pucpr.pucprmunicipio.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.pucpr.pucprmunicipio.model.Municipio


@Dao
interface MunicipioDAO {

    @Query("SELECT * FROM Municipio")
    fun buscaTodos(): LiveData<List<Municipio>>

    @Insert
    fun salva(vararg municipio: Municipio)

    @Query("SELECT * FROM Municipio WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Municipio>

}
