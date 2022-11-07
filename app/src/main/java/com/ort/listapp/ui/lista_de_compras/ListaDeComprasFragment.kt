package com.ort.listapp.ui.lista_de_compras

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.ui.adapters.RealizarCompraAdapter

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

    private lateinit var binding: FragmentListaDeComprasBinding

    private val viewModel: FamilyViewModel by activityViewModels()

    lateinit var popup: AlertDialog
    lateinit var popupBuilder: AlertDialog.Builder
    lateinit var adapterRC: RealizarCompraAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaDeComprasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val btnRealizarCompra = binding.btnRealizarCompra
        val btnEditarLista = binding.btnEditarLista

        viewModel.getFamilia().observe(this, Observer {
            binding.txtPrecioTotalLista.text = "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual()).toString()
            binding.listaCompra.setHasFixedSize(true)
            binding.listaCompra.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.listaCompra.adapter =
                ProductoListadoAdapter(
                    viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()),
                    requireContext(),
                    {removerProducto(it)},
                    {clickSumaYResta(it, 1)},
                    {clickSumaYResta(it, -1)}
                )
        })

        btnRealizarCompra.setOnClickListener{
            realizarCompra()
        }

        btnEditarLista.setOnClickListener {
            editarLista()
        }
    }

    private fun removerProducto(itemLista: ItemLista) {
        viewModel.removerProductoDeListaById(
            viewModel.getIdListaDeComprasActual(),
            itemLista.producto.id
        )
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            viewModel.getIdListaDeComprasActual(),
            producto.producto.id,
            cantidad
        )
    }

    private fun realizarCompra() {
        //oculto los componentes de la lista de compras
        binding.btnListasFavoritas.visibility = View.GONE
        binding.btnCrearProducto.visibility = View.GONE
        binding.btnRealizarCompra.visibility = View.GONE
        binding.btnCrearListaFavorita.visibility = View.GONE
        binding.listaCompra.visibility = View.GONE
        binding.txtPrecioTotalLista.visibility = View.GONE

        //muestro los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.VISIBLE
        binding.btnConfirmarCompra.visibility = View.VISIBLE
        binding.btnEditarLista.visibility = View.VISIBLE
        binding.precioTotalCompra.visibility = View.VISIBLE
        binding.txtConfirmarCompra.visibility = View.VISIBLE

        binding.precioTotalCompra.text = "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual()).toString()

        binding.rvListaRC.setHasFixedSize(true)
        binding.rvListaRC.layoutManager = LinearLayoutManager(requireContext())

        adapterRC = RealizarCompraAdapter(viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()))
        binding.rvListaRC.adapter = adapterRC

        binding.btnConfirmarCompra.setOnClickListener{
            viewModel.realizarCompra()
        }

        /*popupBuilder = AlertDialog.Builder(context)
        val popupView = layoutInflater.inflate(R.layout.popup_realizar_compra, null)
        val reciclerView = popupView.findViewById<RecyclerView>(R.id.rvListaRCOld)
        val btnConfirmar = popupView.findViewById<Button>(R.id.btnConfirmarCompraOld)
        val btnEditarLista = popupView.findViewById<Button>(R.id.btnEditarListaOld)
        val txtPrecioTotal = popupView.findViewById<TextView>(R.id.precioTotalCompraOld)

        txtPrecioTotal.text = "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual()).toString()

        reciclerView.setHasFixedSize(true)
        reciclerView.layoutManager = LinearLayoutManager(requireContext())

        adapterRC = RealizarCompraAdapter(viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()))
        reciclerView.adapter = adapterRC

        popupBuilder.setView(popupView)
        popup = popupBuilder.create()

        btnConfirmar.setOnClickListener{
            viewModel.realizarCompra()
            popup.dismiss()
        }

        btnEditarLista.setOnClickListener {
            popup.dismiss()
        }

        popup.show()*/
    }

    private fun editarLista(){
        //oculto los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.GONE
        binding.btnConfirmarCompra.visibility = View.GONE
        binding.btnEditarLista.visibility = View.GONE
        binding.precioTotalCompra.visibility = View.GONE
        binding.txtConfirmarCompra.visibility = View.GONE

        //muestro los componentes de la lista de compras
        binding.btnListasFavoritas.visibility = View.VISIBLE
        binding.btnCrearProducto.visibility = View.VISIBLE
        binding.btnRealizarCompra.visibility = View.VISIBLE
        binding.btnCrearListaFavorita.visibility = View.VISIBLE
        binding.listaCompra.visibility = View.VISIBLE
        binding.txtPrecioTotalLista.visibility = View.VISIBLE


    }

}