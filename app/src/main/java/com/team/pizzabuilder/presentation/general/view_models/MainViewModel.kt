package com.team.pizzabuilder.presentation.general.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.SearchPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.DeletePizzaUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    val getListPizzaUseCase: GetListPizzaUseCase,
    val deletePizzaUseCase: DeletePizzaUseCase,
    val searchPizzaUseCase: SearchPizzaUseCase
) : ViewModel() {

    val listPizza = getListPizzaUseCase.getListPizza()

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean>
        get() = _isDeleted

    fun deletePizza(pizza: Pizza) {
        viewModelScope.launch {
            deletePizzaUseCase.deletePizza(pizza)
            _isDeleted.value = true
        }
    }

    fun searchPizza(searchParameter: String): LiveData<List<Pizza>> {
        return searchPizzaUseCase.searchPizza(searchParameter)
    }
}