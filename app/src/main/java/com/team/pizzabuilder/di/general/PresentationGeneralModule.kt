package com.team.pizzabuilder.di.general

import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.presentation.general.view_models.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationGeneralModule {

    @Provides
    fun provideMainViewModelFactory(
        getListPizzaUseCase: GetListPizzaUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getListPizzaUseCase = getListPizzaUseCase
        )
    }
}