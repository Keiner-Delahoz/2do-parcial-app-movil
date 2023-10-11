package com.example.a2do_parcial_app_movil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ShowProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_products)

        val linearLayoutMovies = findViewById<LinearLayout>(R.id.linearLayoutProducts)

        // Obtener la lista de películas desde la base de datos
        val movieRepository = ProductRepository(this)
        val moviesList = movieRepository.getAllProducts()  // Implementa esta función en MovieRepository

        // Mostrar los detalles de cada película en un TextView
        moviesList?.forEach { product ->
            val productDetailsLayout = layoutInflater.inflate(R.layout.item_product, null)
            val textViewCode = productDetailsLayout.findViewById<TextView>(R.id.textViewCode)
            val textViewProduct = productDetailsLayout.findViewById<TextView>(R.id.textViewProductName)
            val textViewPrice = productDetailsLayout.findViewById<TextView>(R.id.textViewPrice)
            val textViewDiscount = productDetailsLayout.findViewById<TextView>(R.id.textViewDiscount)

            // Mostrar la información de la película
            textViewProduct.text = "${product.productName}"
            textViewPrice.text = "Código: ${product.code}"
            textViewDiscount.text = "Precio: ${product.price}"
            textViewCode.text = "Descuento: ${product.discount}"

            // Agregar el botón de eliminar con el listener
            val btnDelete = productDetailsLayout.findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                // Eliminar la película cuando se hace clic en el botón
                movieRepository.deleteProduct(product.code)
                // Actualizar la vista
                linearLayoutMovies.removeView(productDetailsLayout)
            }

            // Agregar el botón de editar con el listener
            val btnEdit = productDetailsLayout.findViewById<Button>(R.id.btnEdit)
            btnEdit.setOnClickListener {
                // Crear un Intent para abrir UpdateMovie y pasar datos
                val intent = Intent(this@ShowProducts, UpdateProduct::class.java)

                // Pasar los datos de la película al intent
                intent.putExtra("productName", product.productName)
                intent.putExtra("code", product.code)
                intent.putExtra("price", product.price)
                intent.putExtra("discount", product.discount)

                // Iniciar la actividad UpdateMovie
                startActivity(intent)
            }

            linearLayoutMovies.addView(productDetailsLayout)
        }
    }
}