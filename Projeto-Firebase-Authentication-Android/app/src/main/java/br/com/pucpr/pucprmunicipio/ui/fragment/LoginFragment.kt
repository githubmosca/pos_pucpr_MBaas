package br.com.pucpr.pucprmunicipio.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import br.com.pucpr.pucprmunicipio.R
import br.com.pucpr.pucprmunicipio.extensions.snackBar
import br.com.pucpr.pucprmunicipio.model.Usuario
import br.com.pucpr.pucprmunicipio.repository.Resource
import br.com.pucpr.pucprmunicipio.ui.viewmodel.ComponentesVisuais
import br.com.pucpr.pucprmunicipio.ui.viewmodel.EstadoAppViewModel
import br.com.pucpr.pucprmunicipio.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val controlador by lazy {
        findNavController()
    }
    private val viewModel: LoginViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.login,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        configuraBotaoLogin()
        configuraBotaoCadastro()
    }

    private fun configuraBotaoCadastro() {
        login_botao_cadastrar_usuario.setOnClickListener {
            val direcao = LoginFragmentDirections
                .acaoLoginParaCadastroUsuario()
            controlador.navigate(direcao)
        }
    }

    private fun configuraBotaoLogin() {
        login_botao_logar.setOnClickListener {

            limpaCampos()

            val email: String = login_email.editText?.text.toString()
            val senha: String = login_senha.editText?.text.toString()


            if (validaCampos(email, senha)) {
                autentica(email, senha)
            }

        }
    }

    private fun autentica(email: String, senha: String ) {
        viewModel.autentica(Usuario(email, senha))
            .observe(viewLifecycleOwner, Observer {
                it?.let { recurso ->
                    if (recurso.dado) {
                        vaiParaListaMunicipios()
                    } else {
                        val mensagemErro: String = recurso.erro ?: "Erro durante a autenticacao"
                        view?.snackBar(mensagemErro)
                    }
                }

            })
    }

    private fun validaCampos(email: String, senha: String): Boolean {
        var valido = true

        if (email.isBlank()) {
            login_email.error = "E-mail obrigatório"
            valido = false
        }
        if (senha.isBlank()) {
            login_senha.error = "Senha obrigatório"
            valido = false
        }
        return valido
    }

    private fun limpaCampos() {
        login_email.error = null
        login_senha.error = null
    }

    private fun vaiParaListaMunicipios() {
        val direcao = LoginFragmentDirections.acaoLoginParaListaMunicipios()
        controlador.navigate(direcao)
    }

}