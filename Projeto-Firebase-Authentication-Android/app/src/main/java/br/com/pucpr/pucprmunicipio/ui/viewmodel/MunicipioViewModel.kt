package br.com.pucpr.pucprmunicipio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.pucpr.pucprmunicipio.model.Municipio
import br.com.pucpr.pucprmunicipio.repository.MunicipioRepository

class MunicipiosViewModel(private val repository: MunicipioRepository) : ViewModel() {

    fun buscaTodos(): LiveData<List<Municipio>> = repository.buscaTodos()

}
