package com.example.a2do_parcial_app_movil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onAddButtonClick(view: View) {
        // Crear un intent para iniciar la actividad FormMovie
        val intent = Intent(this, FormProduct::class.java)
        startActivity(intent)
    }

    // Método que se llama cuando se hace clic en el botón "Show"
    fun onShowButtonClick(view: View) {
        // Crear un intent para iniciar la actividad ShowMovies
        val intent = Intent(this, ShowProducts::class.java)
        startActivity(intent)
    }
}