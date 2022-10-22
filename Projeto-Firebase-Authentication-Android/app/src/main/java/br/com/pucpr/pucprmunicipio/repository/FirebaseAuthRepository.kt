package br.com.pucpr.pucprmunicipio.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pucpr.pucprmunicipio.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.lang.IllegalArgumentException

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun desloga() {
        firebaseAuth.signOut()
    }


    fun cadastra(usuario: Usuario): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            val tarefa =
                firebaseAuth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            tarefa.addOnSuccessListener {
                Log.i(TAG, "cadastra: Usuário cadastrado com sucesso")
                liveData.value = Resource(true)
            }
            tarefa.addOnFailureListener { exception: Exception ->
                Log.i(TAG, "cadastra: Cadastro usuario com falha")
                val mensgemErro: String = devolveErroDeCadastro(exception)
                liveData.value = Resource(false, mensgemErro)

            }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha não podem estar em branco")
        }
        return liveData

    }

    private fun devolveErroDeCadastro(exception: Exception): String = when (exception) {
        is FirebaseAuthWeakPasswordException -> "Senha Fraca - Precisa de 6 digitos"
        is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
        is FirebaseAuthUserCollisionException -> "E-mail já existente"
        else -> "Erro Desconhecido"
    }

    fun estaLogado(): Boolean {
        val usuarioFirebase: FirebaseUser? = firebaseAuth.currentUser
        if (usuarioFirebase != null) {
            return true
        }
        return false

    }

    fun autentica(usuario: Usuario): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(usuario.email, usuario.senha)
                .addOnCompleteListener { tarefa ->
                    if (tarefa.isSuccessful) {
                        liveData.value = Resource(true)
                    } else {
                        Log.e(TAG, "Autentica", tarefa.exception)

                        val mensagemErro: String = devolveErroDeAutenticacao(tarefa.exception)
                        liveData.value = Resource(false, mensagemErro)
                    }
                }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha vazio(s)")
        }
        return liveData
    }

    private fun devolveErroDeAutenticacao(exception: Exception?): String = when (exception) {

        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "E-mail ou Senha incorretos"
        else -> "Erro desconhecido"
    }

    fun usuario(): LiveData<Usuario> {
        val liveData = MutableLiveData<Usuario>()

        firebaseAuth.currentUser?.let {firebaseUser ->
            firebaseUser.email?.let { email: String ->
                liveData.value = Usuario(email )
            }

        }
        return liveData
    }

}