package br.com.pucpr.pucprmunicipio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.pucpr.pucprmunicipio.model.Usuario
import br.com.pucpr.pucprmunicipio.repository.FirebaseAuthRepository

class MinhaContaViewModel(repository: FirebaseAuthRepository): ViewModel() {

    val usuario: LiveData<Usuario> = repository.usuario()
}