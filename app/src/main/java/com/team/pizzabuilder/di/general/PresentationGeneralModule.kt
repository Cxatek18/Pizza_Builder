package com.team.pizzabuilder.di.general

import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.SearchPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.DeletePizzaUseCase
import com.team.pizzabuilder.presentation.general.view_models.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationGeneralModule {

    @Provides
    fun provideMainViewModelFactory(
        getListPizzaUseCase: GetListPizzaUseCase,
        deletePizzaUseCase: DeletePizzaUseCase,
        searchPizzaUseCase: SearchPizzaUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(
            getListPizzaUseCase = getListPizzaUseCase,
            deletePizzaUseCase = deletePizzaUseCase,
            searchPizzaUseCase = searchPizzaUseCase
        )
    }
}