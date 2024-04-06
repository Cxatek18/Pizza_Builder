package com.team.pizzabuilder.domain.general.models

data class Pizza(
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
