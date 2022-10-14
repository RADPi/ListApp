package com.ort.listapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.entities.Producto

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var v: View
    var productos : MutableList<Producto> = ArrayList<Producto>()

    lateinit var listaCompra: RecyclerView

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        listaCompra = v.findViewById(R.id.listaCompra)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}