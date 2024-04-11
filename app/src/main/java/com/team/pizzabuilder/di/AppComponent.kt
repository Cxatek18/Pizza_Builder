package com.team.pizzabuilder.di

import com.team.pizzabuilder.di.general.DataGeneralModule
import com.team.pizzabuilder.di.general.DomainGeneralModule
import com.team.pizzabuilder.di.general.PresentationGeneralModule
import com.team.pizzabuilder.di.owner.PresentationOwnerModule
import com.team.pizzabuilder.presentation.general.fragments.DetailPizzaFragment
import com.team.pizzabuilder.presentation.general.fragments.MainFragment
import com.team.pizzabuilder.presentation.owner.fragments.CreatePizzaFragment
import com.team.pizzabuilder.presentation.owner.fragments.UpdatePizzaFragment
import dagger.Component

@Component(
    modules = [
        DataGeneralModule::class,
        DomainGeneralModule::class,
        PresentationGeneralModule::class,
        AppModule::class,
        PresentationOwnerModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: MainFragment)

    fun inject(fragment: CreatePizzaFragment)

    fun inject(fragment: UpdatePizzaFragment)

    fun inject(fragment: DetailPizzaFragment)
}