package com.team.pizzabuilder.di.owner

import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.CreatePizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.UpdatePizzaUseCase
import com.team.pizzabuilder.presentation.general.view_models.DetailPizzaViewModelFactory
import com.team.pizzabuilder.presentation.owner.view_models.CreatePizzaViewModelFactory
import com.team.pizzabuilder.presentation.owner.view_models.UpdatePizzaViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationOwnerModule {

    @Provides
    fun provideCreatePizzaViewModelFactory(
        createPizzaUseCase: CreatePizzaUseCase
    ): CreatePizzaViewModelFactory {
        return CreatePizzaViewModelFactory(
            createPizzaUseCase = createPizzaUseCase
        )
    }

    @Provides
    fun provideUpdatePizzaViewModelFactory(
        updatePizzaUseCase: UpdatePizzaUseCase,
        getPizzaUseCase: GetPizzaUseCase
    ): UpdatePizzaViewModelFactory {
        return UpdatePizzaViewModelFactory(
            updatePizzaUseCase = updatePizzaUseCase,
            getPizzaUseCase = getPizzaUseCase
        )
    }

    @Provides
    fun provideDetailPizzaViewModelFactory(
        getPizzaUseCase: GetPizzaUseCase
    ): DetailPizzaViewModelFactory {
        return DetailPizzaViewModelFactory(
            getPizzaUseCase = getPizzaUseCase
        )
    }
}