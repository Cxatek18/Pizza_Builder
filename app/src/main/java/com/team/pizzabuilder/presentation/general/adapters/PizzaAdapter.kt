package com.team.pizzabuilder.presentation.general.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.team.pizzabuilder.databinding.PizzaItemOwnerBinding
import com.team.pizzabuilder.domain.general.models.Pizza

class PizzaAdapter : ListAdapter<Pizza, PizzaViewHolder>(PizzaDiffCallback) {

    var onCLickDeletePizza: ((pizza: Pizza) -> Unit)? = null
    var onClickUpdatePizza: ((pizza: Pizza) -> Unit)? = null
    var onClickDetailPizza: ((pizzaId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val binding = PizzaItemOwnerBinding.inflate(
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

        listeningOnClickDeleteBtn(holder, pizza)
        listeningOnClickUpdateBtn(holder, pizza)
        listeningOnClickDetailBtn(holder, pizza.id)
    }

    private fun listeningOnClickDeleteBtn(holder: PizzaViewHolder, pizza: Pizza) {
        holder.binding.btnPizzaDelete.setOnClickListener {
            onCLickDeletePizza?.invoke(pizza)
            true
        }
    }

    private fun listeningOnClickUpdateBtn(holder: PizzaViewHolder, pizza: Pizza) {
        holder.binding.btnPizzaUpdate.setOnClickListener {
            onClickUpdatePizza?.invoke(pizza)
            true
        }
    }

    private fun listeningOnClickDetailBtn(holder: PizzaViewHolder, pizzaId: Int) {
        holder.binding.btnPizzaDetail.setOnClickListener {
            onClickDetailPizza?.invoke(pizzaId)
            true
        }
    }
}