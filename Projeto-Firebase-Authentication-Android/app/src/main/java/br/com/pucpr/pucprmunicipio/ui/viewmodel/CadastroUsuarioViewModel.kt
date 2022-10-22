package br.com.pucpr.pucprmunicipio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.pucpr.pucprmunicipio.model.Usuario
import br.com.pucpr.pucprmunicipio.repository.FirebaseAuthRepository
import br.com.pucpr.pucprmunicipio.repository.Resource

class CadastroUsuarioViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {

    fun cadastra( usuario: Usuario) : LiveData<Resource<Boolean>> {
       return  repository.cadastra(usuario)

    }
}