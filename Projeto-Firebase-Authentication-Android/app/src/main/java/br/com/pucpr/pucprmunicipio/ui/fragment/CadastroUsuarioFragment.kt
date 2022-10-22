package br.com.pucpr.pucprmunicipio.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.pucpr.pucprmunicipio.R
import br.com.pucpr.pucprmunicipio.extensions.snackBar
import br.com.pucpr.pucprmunicipio.model.Usuario
import br.com.pucpr.pucprmunicipio.repository.Resource
import br.com.pucpr.pucprmunicipio.ui.viewmodel.CadastroUsuarioViewModel
import br.com.pucpr.pucprmunicipio.ui.viewmodel.ComponentesVisuais
import br.com.pucpr.pucprmunicipio.ui.viewmodel.EstadoAppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CadastroUsuarioFragment : Fragment() {

    private val controlador by lazy {
        findNavController()
    }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val viewModel: CadastroUsuarioViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.cadastro_usuario,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        configuraBotaoCadastro()
    }

    private fun configuraBotaoCadastro() {
        cadastro_usuario_botao_cadastrar.setOnClickListener {

            LimpaTodosCampos()

            val email: String = cadastro_usuario_email.editText?.text.toString()
            val senha: String = cadastro_usuario_senha.editText?.text.toString()
            val confirmaSenha: String = cadastro_usuario_confirma_senha.editText?.text.toString()

            val valido: Boolean = validaCampos(email, senha, confirmaSenha)

            if (valido) {
                cadastra(Usuario(email, senha))
            }
        }
    }

    private fun cadastra(usuario: Usuario) {
        viewModel.cadastra(usuario).observe(viewLifecycleOwner, Observer {
            it?.let { recurso: Resource<Boolean> ->
                if (recurso.dado) {
                    view?.snackBar("Ocorreu um falha no cadastro")
                    controlador.popBackStack()
            } else {
            val mensagemErro: String = recurso.erro ?: "Ocorreu um falha no cadastro"
            view?.snackBar(mensagemErro)
        }
        }
        })
    }

    private fun validaCampos(
        email: String,
        senha: String,
        confirmaSenha: String
    ): Boolean {
        var valido = true

        if (email.isBlank()) {
            cadastro_usuario_email.error = "E-mail em branco"
            valido = false
        }
        if (senha.isBlank()) {
            cadastro_usuario_senha.error = "Senha em branco"
            valido = false
        }

        if (confirmaSenha.isBlank()) {
            cadastro_usuario_confirma_senha.error = "Senha em branco"
            valido = false
        }

        if (senha != confirmaSenha) {
            cadastro_usuario_confirma_senha.error = "Senhas não conferem"
            valido = false
        }
        return valido
    }

    private fun LimpaTodosCampos() {
        cadastro_usuario_email.error = null
        cadastro_usuario_senha.error = null
        cadastro_usuario_confirma_senha.error = null
    }
}