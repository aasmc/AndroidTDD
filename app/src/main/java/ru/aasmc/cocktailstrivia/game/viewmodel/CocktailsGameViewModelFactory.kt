package ru.aasmc.cocktailstrivia.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aasmc.cocktailstrivia.common.repository.CocktailsRepository
import ru.aasmc.cocktailstrivia.game.factory.CocktailsGameFactory

//class CocktailsGameViewModelFactory(private val repository: CocktailsRepository,
//                                    private val gameFactory: CocktailsGameFactory
//)
//  : ViewModelProvider.Factory {
//  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//    return CocktailsGameViewModel(repository, gameFactory) as T
//  }
//}