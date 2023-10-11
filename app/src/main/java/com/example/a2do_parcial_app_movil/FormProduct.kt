package com.example.a2do_parcial_app_movil

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FormProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)

        // Obtener referencias a los elementos de la interfaz de usuario
        val etCode = findViewById<EditText>(R.id.etCode)
        val etProductName = findViewById<EditText>(R.id.etProductName)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etDiscount = findViewById<EditText>(R.id.etDiscount)
        val btnAddProduct = findViewById<Button>(R.id.btnAddProduct)
        val btnCancelProduct = findViewById<Button>(R.id.btnCancelProduct)

        // Configurar acción para el botón "Add Movie"
        btnAddProduct.setOnClickListener {
            val code = etCode.text.toString()
            val productName = etProductName.text.toString()
            val price = etPrice.text.toString()
            val discount = etDiscount.text.toString()

            if (code.isBlank() || productName.isBlank() || price.isBlank() || discount.isBlank()) {
                Toast.makeText(this, "No pueden quedar campos vacios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(code, productName, price, discount)

            // Obtener una instancia de MovieRepository
            val productRepository = ProductRepository(this)

            // Llamar a la función para agregar la película a la base de datos
            productRepository.addProduct(product)

            // Mostrar un mensaje de confirmación
            Toast.makeText(this, "Producto agregado correctamente!", Toast.LENGTH_SHORT).show()

            // Limpiar los campos después de enviar los datos
            etProductName.text.clear()
            etPrice.text.clear()
            etDiscount.text.clear()

            // Ocultar el teclado virtual
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etCode.windowToken, 0)

            // Solicitar el foco en etCode
            etCode.requestFocus()

            // Leer la película recién agregada y mostrar sus detalles

            /* val addedProduct = productRepository.getProduct(code)
            if (addedProduct != null) {
                val movieDetailsMessage = "Added Movie Details:\n" +
                        "Code: ${addedProduct.code}\n" +
                        "Product name: ${addedProduct.productName}\n" +
                        "Price: ${addedProduct.price}\n" +
                        "Discount: ${addedProduct.discount}"
                Toast.makeText(this, movieDetailsMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to retrieve added product details.", Toast.LENGTH_LONG).show()
            } */

        }

        // Configurar acción para el botón "Cancel Movie"
        btnCancelProduct.setOnClickListener {
            etCode.text.clear()
            etProductName.text.clear()
            etPrice.text.clear()
            etDiscount.text.clear()
        }
    }
}