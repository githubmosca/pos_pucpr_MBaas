package br.com.pucpr.pucprmunicipio.repository

import androidx.lifecycle.LiveData
import br.com.pucpr.pucprmunicipio.database.dao.MunicipioDAO
import br.com.pucpr.pucprmunicipio.model.Municipio

class MunicipioRepository(private val dao: MunicipioDAO) {

    fun buscaTodos(): LiveData<List<Municipio>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Municipio> = dao.buscaPorId(id)

}
