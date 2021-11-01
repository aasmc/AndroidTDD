package ru.aasmc.cocktailstrivia

import android.app.Application
import android.content.Context
import ru.aasmc.cocktailstrivia.common.network.CocktailsApi
import ru.aasmc.cocktailstrivia.common.repository.CocktailsRepository
import ru.aasmc.cocktailstrivia.common.repository.CocktailsRepositoryImpl
import ru.aasmc.cocktailstrivia.game.factory.CocktailsGameFactory
import ru.aasmc.cocktailstrivia.game.factory.CocktailsGameFactoryImpl

class CocktailsApplication : Application() {
  val repository: CocktailsRepository by lazy {
    CocktailsRepositoryImpl(
        CocktailsApi.create(),
        getSharedPreferences("Cocktails", Context.MODE_PRIVATE)
    )
  }

  val gameFactory: CocktailsGameFactory by lazy {
    CocktailsGameFactoryImpl(repository)
  }
}