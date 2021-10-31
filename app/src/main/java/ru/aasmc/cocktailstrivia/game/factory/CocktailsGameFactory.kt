package ru.aasmc.cocktailstrivia.game.factory

import ru.aasmc.cocktailstrivia.common.network.Cocktail
import ru.aasmc.cocktailstrivia.common.repository.CocktailsRepository
import ru.aasmc.cocktailstrivia.common.repository.RepositoryCallback
import ru.aasmc.cocktailstrivia.game.model.Game
import ru.aasmc.cocktailstrivia.game.model.Question
import ru.aasmc.cocktailstrivia.game.model.Score

interface CocktailsGameFactory {
    fun buildGame(callback: Callback)

    interface Callback {
        fun onSuccess(game: Game)
        fun onError()
    }
}

class CocktailsGameFactoryImpl(
    private val repository: CocktailsRepository
) : CocktailsGameFactory {
    override fun buildGame(callback: CocktailsGameFactory.Callback) {
        repository.getAlcoholic(
            object : RepositoryCallback<List<Cocktail>, String> {
                override fun onSuccess(cocktailList: List<Cocktail>) {
                    val questions = buildQuestions(cocktailList)
                    val score = Score(repository.getHighScore())
                    val game = Game(questions, score)
                    callback.onSuccess(game)
                }

                override fun onError(e: String) {
                    callback.onError()
                }
            }
        )
    }

    private fun buildQuestions(cocktailList: List<Cocktail>): List<Question> =
        cocktailList.map { cocktail ->
            val otherCocktail = cocktailList.shuffled().first { it != cocktail }
            Question(
                cocktail.strDrink,
                otherCocktail.strDrink,
                cocktail.strDrinkThumb
            )
        }
}




















