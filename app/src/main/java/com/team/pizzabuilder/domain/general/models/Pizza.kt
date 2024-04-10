package com.team.pizzabuilder.domain.general.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza(
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val id: Int = UNDEFINED_ID
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
