package br.com.pucpr.pucprmunicipio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.pucpr.pucprmunicipio.model.Usuario
import br.com.pucpr.pucprmunicipio.repository.FirebaseAuthRepository
import br.com.pucpr.pucprmunicipio.repository.Resource

class LoginViewModel(

    private val firebaseAuthRepository:FirebaseAuthRepository
    ) : ViewModel() {

    fun autentica(usuario: Usuario) : LiveData<Resource<Boolean>>{
       return firebaseAuthRepository.autentica(usuario)
    }

    fun desloga() {
        firebaseAuthRepository.desloga()
    }

    fun estaLogado(): Boolean = firebaseAuthRepository.estaLogado()

    fun naoEstaLogado(): Boolean = !estaLogado()

}
