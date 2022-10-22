package br.com.pucpr.pucprmunicipio.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.pucpr.pucprmunicipio.repository.MunicipioRepository


class DetalhesMunicipioViewModel(
    municipioId: Long,
    repository: MunicipioRepository
) : ViewModel() {

    val municipioEncontrado = repository.buscaPorId(municipioId)

}