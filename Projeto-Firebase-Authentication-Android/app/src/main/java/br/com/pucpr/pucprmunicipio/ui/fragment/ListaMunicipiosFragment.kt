package br.com.pucpr.pucprmunicipio.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.pucpr.pucprmunicipio.ui.fragment.ListaMunicipiosFragmentDirections
import br.com.pucpr.pucprmunicipio.R
import br.com.pucpr.pucprmunicipio.ui.recyclerview.adapter.MunicipioAdapter
import br.com.pucpr.pucprmunicipio.ui.viewmodel.ComponentesVisuais
import br.com.pucpr.pucprmunicipio.ui.viewmodel.EstadoAppViewModel
import br.com.pucpr.pucprmunicipio.ui.viewmodel.MunicipiosViewModel
import kotlinx.android.synthetic.main.lista_municipio.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListaMunicipiosFragment : BaseFragment() {

    private val viewModel: MunicipiosViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val adapter: MunicipioAdapter by inject()
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaMunicipios()
    }

    private fun buscaMunicipios() {
        viewModel.buscaTodos().observe(this, Observer { municipiosEncontrados ->
            municipiosEncontrados?.let {
                adapter.atualiza(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.lista_municipio,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(
            appBar = true,
            bottomNavigation = true)
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        lista_municipios_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = { municipioSelecionado ->
            vaiParaDetalhesDoMunicipio(municipioSelecionado.id)
        }
        lista_municipios_recyclerview.adapter = adapter
    }

    private fun vaiParaDetalhesDoMunicipio(municipioId: Long) {
        val direcao = ListaMunicipiosFragmentDirections
            .acaoListaMunicipiosParaDetalhesMunicipio(municipioId)
        controlador.navigate(direcao)
    }

}
