package br.com.pucpr.pucprmunicipio.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.pucpr.pucprmunicipio.ui.fragment.DetalhesMunicipioFragmentArgs
import br.com.pucpr.pucprmunicipio.R

import br.com.pucpr.pucprmunicipio.ui.viewmodel.ComponentesVisuais
import br.com.pucpr.pucprmunicipio.ui.viewmodel.DetalhesMunicipioViewModel

import br.com.pucpr.pucprmunicipio.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.detalhes_municipio.*

import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesMunicipioFragment : BaseFragment() {

    private val argumentos by navArgs<DetalhesMunicipioFragmentArgs>()
    private val municipioId by lazy {
        argumentos.municipioId
    }
    private val viewModel: DetalhesMunicipioViewModel by viewModel { parametersOf(municipioId) }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_municipio,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(appBar = true)
        buscaMunicipio()

    }


    private fun buscaMunicipio() {
        viewModel.municipioEncontrado.observe(viewLifecycleOwner, Observer {
            it?.let { municipio ->

                detalhes_municipio_nome.text = municipio.nome
                detalhes_municipio_codigo.text = municipio.codigo.toString()
            }
        })
    }

}