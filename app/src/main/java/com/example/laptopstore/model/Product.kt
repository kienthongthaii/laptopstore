package com.example.laptopstore.model

// Product.kt
data class Product(
    val imageResId: Int,
    val code: String,
    val name: String,
    val price: Double,
    val type: String,
    val color: String,
    val brand: String,
    val series: String,
    val cpu: String,
    val ram: String,
    val gpu: String,
    val screen: String,
    val memory: String,
    val material: String,
    val battery: String,
    val productionYear: Int,
    val description: String
)
