package com.example.a2do_parcial_app_movil

import java.io.Serializable

data class Product(val code: String, val productName: String, val price: String, val discount: String) :
    Serializable