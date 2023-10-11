package com.example.a2do_parcial_app_movil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        // Obtener referencias a los elementos de la interfaz de usuario
        val etEditCode = findViewById<EditText>(R.id.etEditCode)
        val etEditProductName = findViewById<EditText>(R.id.etEditProductName)
        val etEditPrice = findViewById<EditText>(R.id.etEditPrice)
        val etEditDiscount = findViewById<EditText>(R.id.etEditDiscount)
        val btnEditProduct = findViewById<Button>(R.id.btnEditProduct)
        val btnEditCancelProduct = findViewById<Button>(R.id.btnEditCancelProduct)


        // Obtener datos de la película de la intent
        val code = intent.getStringExtra("code")
        val productName = intent.getStringExtra("productName")
        val price = intent.getStringExtra("price")
        val discount = intent.getStringExtra("discount")

        // Mostrar datos en los campos
        etEditCode.setText(code)
        etEditProductName.setText(productName)
        etEditPrice.setText(price)
        etEditDiscount.setText(discount)

        // Deshabilitar interacciones para etEditCode
        etEditCode.isEnabled = false
        etEditCode.isFocusable = false
        etEditCode.isClickable = false


        // Eliminar campos
        btnEditCancelProduct.setOnClickListener {
            // Limpiar los campos
            etEditProductName.text.clear()
            etEditPrice.text.clear()
            etEditDiscount.text.clear()  // Establecer la selección del Spinner al primer elemento
        }

        //Editar los datos
        btnEditProduct.setOnClickListener {
            // Obtener los valores de los campos
            val updatedCode = etEditCode.text.toString()
            val updatedProductName = etEditProductName.text.toString()
            val updatedPrice = etEditPrice.text.toString()
            val updatedDiscount = etEditDiscount.text.toString()

            if (updatedProductName.isBlank() || updatedPrice.isBlank() || updatedDiscount.isBlank()) {
                Toast.makeText(this, "No pueden quedar campos vacios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear un objeto Movie con los valores actualizados
            val updatedProduct = Product(updatedCode, updatedProductName, updatedPrice, updatedDiscount)

            // Obtener una instancia de MovieRepository
            val productRepository = ProductRepository(this)

            // Llamar al método para actualizar la película
            productRepository.updateProduct(updatedProduct)

            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Producto actualizado correctamente!", Toast.LENGTH_SHORT).show()

            // Enviar la película actualizada de vuelta a ShowMovies
            val resultIntent = Intent()
            resultIntent.putExtra("updated_movie", updatedProduct)
            setResult(Activity.RESULT_OK, resultIntent)

            // Iniciar la actividad MainActivity
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

            finish() // Finalizar la actividad UpdateMovie
        }
    }
}