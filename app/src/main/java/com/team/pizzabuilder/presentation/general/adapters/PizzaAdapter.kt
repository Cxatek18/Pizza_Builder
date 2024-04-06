package com.team.pizzabuilder.presentation.general.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.team.pizzabuilder.databinding.PizzaItemBinding
import com.team.pizzabuilder.domain.general.models.Pizza

class PizzaAdapter : ListAdapter<Pizza, PizzaViewHolder>(PizzaDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val binding = PizzaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PizzaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val pizza = getItem(position)
        with(holder.binding) {
            with(pizza) {
                pizzaTitle.text = pizza.name
                pizzaDescription.text = pizza.description
                pizzaPrice.text = String.format("%s руб.", pizza.price)
            }
        }
        Glide.with(holder.itemView)
            .load(pizza.imageUrl)
            .into(holder.binding.pizzaImage)
    }
}