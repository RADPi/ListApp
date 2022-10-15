package com.ort.listapp.ui.productos

import android.app.AlertDialog
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoAdapter
import com.ort.listapp.domain.model.Producto

class ProductosFragment : Fragment() {

    companion object {
        fun newInstance() = ProductosFragment()

    }
    lateinit var v: View
    lateinit var popUp : AlertDialog
    lateinit var popupBuilder : AlertDialog.Builder
    var listaProdsFavs : MutableList<Producto> = ArrayList<Producto>()
    var listaProdsPersonalizados : MutableList<Producto> = ArrayList<Producto>()
    var listaStock : MutableList<Producto> = ArrayList<Producto>()

    lateinit var recProdsFavoritos:RecyclerView
    lateinit var recProdsPersonalizados:RecyclerView
    lateinit var recStock:RecyclerView
    private lateinit var viewModel: ProductosViewModel
    lateinit var btnFiltrado : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_productos, container, false)
        recProdsFavoritos = v.findViewById(R.id.recViewProductos)
        recProdsPersonalizados = v.findViewById(R.id.listaProdsPersonalizados)
        recStock = v.findViewById(R.id.recListaStock)
        btnFiltrado = v.findViewById(R.id.btnFiltrarProductos)
        cargarStock()
        cargarProdsFav()
        cargarProdsPersonalizados()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductosViewModel::class.java)
        //viewModel.cargarProdsFavs()
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        recProdsFavoritos.setHasFixedSize(true)
        recProdsFavoritos.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
       /* viewModel.cargarProdsFavs().observe(viewLifecycleOwner, Observer{productosFavs->
            recProdsFavoritos.adapter = ProductoAdapter(productosFavs as MutableList<Producto>,requireContext()){ pos->
                onItemClick(pos)
            }

        })*/
        recProdsPersonalizados.setHasFixedSize(true)
        recProdsPersonalizados.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        recStock.setHasFixedSize(true)
        recStock.layoutManager = GridLayoutManager(requireContext(),3)

        recProdsFavoritos.adapter = ProductoAdapter(listaProdsFavs,requireContext()){ prod->
            onItemClick(prod)
        }
        recProdsPersonalizados.adapter =  ProductoAdapter(listaProdsPersonalizados,requireContext()){ prod->
            onItemClick(prod)
        }
        recStock.adapter = ProductoAdapter(listaStock,requireContext()) { prod->
            onItemClick(prod)
        }

        btnFiltrado.setOnClickListener{

            recProdsFavoritos.layoutManager = GridLayoutManager(requireContext(),3)
        }
    }


    fun onItemClick ( producto: Producto)  {
        popupBuilder = AlertDialog.Builder(context)
        var popUpView = getLayoutInflater().inflate(R.layout.popup_producto_layout,null)
        var botonAgregar = popUpView.findViewById<Button>(R.id.btn_sumar_agregarprod)
        var botonRestar = popUpView.findViewById<Button>(R.id.btn_restar_agregarprod)
        var cantidad = popUpView.findViewById<TextView>(R.id.txt_cantidad_agregarprod)
        var imagen = popUpView.findViewById<ImageView>(R.id.img_producto_popup)
        var nombreProd =  popUpView.findViewById<TextView>(R.id.txt_nom_prod_popup)
        var precioProducto =  popUpView.findViewById<TextView>(R.id.txt_precio_prod_popup)
        var subtotal = popUpView.findViewById<TextView>(R.id.txt_subtotal_popup)
        var btnCerrar = popUpView.findViewById<ImageButton>(R.id.btn_cerrar_popup)
        var cantActual = 0


       //cantidad.setText(0)

        fun actualizarSubtotal(){
            if(cantActual > 0){
                subtotal.text = "Subtotal: $"+producto.precioMax * cantActual
            }else{
                subtotal.text= ""
            }
        }
        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        popUp.show()
        nombreProd.text = producto.nombre
        precioProducto.text =  "$"+producto.precioMax.toString()
        Glide.with(popUpView).load(producto.imgURL()).into(imagen)
        botonAgregar.setOnClickListener {
            cantActual++
            cantidad.text = cantActual.toString()
            actualizarSubtotal()

        }
        botonRestar.setOnClickListener {
            if(cantActual > 0) {
                cantActual--
                cantidad.text = cantActual.toString()
                actualizarSubtotal()
            }
        }

        btnCerrar.setOnClickListener{
            popUp.dismiss()
        }

    }

    fun cargarStock(){
        var i = 0
        while(i<15){
            listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
            i += 1
        }
    }
    fun cargarProdsFav(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    }
    fun cargarProdsPersonalizados(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg" ))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg") )
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    }

    fun crearPopUp(){

        popupBuilder = AlertDialog.Builder(context)
        var popUpView = getLayoutInflater().inflate(R.layout.popup_producto_layout,null)
        var botonAgregar = popUpView.findViewById<Button>(R.id.btn_sumar_agregarprod)
        var botonRestar = popUpView.findViewById<Button>(R.id.btn_restar_agregarprod)
        var cantidad = popUpView.findViewById<EditText>(R.id.txt_precio_prod_popup)

        cantidad.inputType = 0


        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        popUp.show()



    }
}

