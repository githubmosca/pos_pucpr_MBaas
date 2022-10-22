package br.com.pucpr.pucprmunicipio.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.pucpr.pucprmunicipio.R
import br.com.pucpr.pucprmunicipio.model.Municipio


import kotlinx.android.synthetic.main.item_municipio.view.*

class MunicipioAdapter(
    private val context: Context,
    private val municipios: MutableList<Municipio> = mutableListOf(),
    var onItemClickListener: (municipio: Municipio) -> Unit = {}
) : RecyclerView.Adapter<MunicipioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_municipio,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = municipios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(municipios[position])
    }

    fun atualiza(municipiosNovos: List<Municipio>) {
        notifyItemRangeRemoved(0, municipios.size)
        municipios.clear()
        municipios.addAll(municipiosNovos)
        notifyItemRangeInserted(0, municipios.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var municipio: Municipio
        private val campoNome by lazy { itemView.item_municipio_nome }
        private val campoCodigo by lazy { itemView.item_municipio_codigo }

        init {
            itemView.setOnClickListener {
                if (::municipio.isInitialized) {
                    onItemClickListener(municipio)
                }
            }
        }

        fun vincula(municipio: Municipio) {
            this.municipio = municipio
            campoNome.text = municipio.nome
            campoCodigo.text = municipio.codigo.toString()
        }

    }

}
