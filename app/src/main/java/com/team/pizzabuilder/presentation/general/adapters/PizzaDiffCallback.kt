package com.team.pizzabuilder.presentation.general.adapters

import androidx.recyclerview.widget.DiffUtil
import com.team.pizzabuilder.domain.general.models.Pizza

object PizzaDiffCallback : DiffUtil.ItemCallback<Pizza>() {

    override fun areItemsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem == newItem
    }
}